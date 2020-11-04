package com.eric.gateway.outbound.okhttp;

import com.eric.gateway.commons.AppConstants;
import com.eric.gateway.commons.NamedThreadFactory;
import com.eric.gateway.commons.utils.SystemUtils;
import com.eric.gateway.outbound.OutboundHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

import static com.eric.gateway.commons.AppConstants.BLOCKING_QUEUE_SIZE;
import static com.eric.gateway.commons.AppConstants.KEEP_ALIVE_TIME;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


public class OkhttpOutboundHandler implements OutboundHandler {
    // This builds a default client that shares the same connection pool, thread pools, and configuration
    private OkHttpClient client;
    private ExecutorService proxyService;
    private String backendUrl;

    public OkhttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl;
        int cores = SystemUtils.getCoreNum();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(BLOCKING_QUEUE_SIZE),
                new NamedThreadFactory("proxyService"), handler);
        client = new OkHttpClient();
    }

    @Override
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        if(fullRequest == null) {
            exceptionCaught(ctx, new NullPointerException("fullRequest is null!!"));
            return;
        }
        final String url = this.backendUrl + fullRequest.uri();
        proxyService.submit(() -> doGet(fullRequest, ctx, url));
    }

    public void doGet(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx, String url) {
        // extract all the K-V pairs from the fullHttpRequest and add them to the newly created request.
        Headers headers = extractHeadersFrom(fullHttpRequest);
        Request request = new Request.Builder().url(url).headers(headers).build();
        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            handleResponse(response, ctx);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleResponse(final Response okHttpResponse, final ChannelHandlerContext ctx) {
        FullHttpResponse response = null;
        try {
            byte[] body = okHttpResponse.body().bytes();

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(okHttpResponse.header("Content-Length")));

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            ctx.write(response);
            ctx.flush();
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private Headers extractHeadersFrom(FullHttpRequest fullHttpRequest) {
        Map<String, String> headerMap = new HashMap<>();
        HttpHeaders headers = fullHttpRequest.headers();
        Iterator<Map.Entry<String, String>> it = headers.iteratorAsString();
        while (it.hasNext()) {
            Map.Entry<String, String> header = it.next();
            headerMap.put(header.getKey().toUpperCase(), header.getValue());
        }
        return Headers.of(headerMap);

    }


}
