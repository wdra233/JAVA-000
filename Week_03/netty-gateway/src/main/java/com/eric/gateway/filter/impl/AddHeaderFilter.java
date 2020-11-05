package com.eric.gateway.filter.impl;

import com.eric.gateway.filter.HttpMessageFilter;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddHeaderFilter implements HttpMessageFilter {
    @Override
    public boolean filter(final FullHttpMessage msg) {
        msg.headers().add("nio", "eric");
        // always return true
        return true;
    }
}
