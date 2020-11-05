package com.eric.gateway.outbound.okhttp;

import com.eric.gateway.commons.utils.FullHttpMessageUtils;
import com.eric.gateway.outbound.OutboundHandler;
import com.eric.gateway.router.RandomRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
public class OkhttpOutboundHandler implements OutboundHandler {
    // This builds a default client that shares the same connection pool, thread pools, and configuration
    private static final OkHttpClient client = new OkHttpClient();
    private String backendUrl;

    public OkhttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl;
    }

    @Override
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        if(fullRequest == null) {
            exceptionCaught(ctx, new NullPointerException("fullRequest is null!!"));
            return;
        }
        String url = this.backendUrl + fullRequest.uri();
        doGet(fullRequest, ctx, url);
    }

    public void doGet(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx, String url) {
        // extract all the K-V pairs from the fullHttpRequest and add them to the newly created request.
        Headers headers = Headers.of(FullHttpMessageUtils.extractHeadersFrom(fullHttpRequest));
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
            response = (FullHttpResponse) FullHttpMessageUtils.replaceBody(generateResponse(), response);

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

    @Override
    public String generateResponse() {
        return "Hello World from: " + backendUrl;
    }

}
