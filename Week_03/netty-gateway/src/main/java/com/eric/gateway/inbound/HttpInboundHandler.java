package com.eric.gateway.inbound;


import com.eric.gateway.filter.impl.AddHeaderFilter;
import com.eric.gateway.filter.impl.MessageFilterChain;
import com.eric.gateway.outbound.OutboundHandler;
import com.eric.gateway.outbound.httpclient4.HttpOutboundHandler;
import com.eric.gateway.outbound.okhttp.OkhttpOutboundHandler;
import com.eric.gateway.router.HttpEndpointRouter;
import com.eric.gateway.router.RandomRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private OutboundHandler handler;
    private static HttpEndpointRouter router = new RandomRouter();
    public static List<String> endpoints = Arrays.asList(
            "http://localhost:8088",
            "http://localhost:8081",
            "http://localhost:8082");
    public HttpInboundHandler() {
        handler = new OkhttpOutboundHandler(router.route(endpoints));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            // building filter chain
            MessageFilterChain filterChain = new MessageFilterChain();
            filterChain.add(new AddHeaderFilter());
            filterChain.filter(fullRequest);
            handler.handle(fullRequest, ctx);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
