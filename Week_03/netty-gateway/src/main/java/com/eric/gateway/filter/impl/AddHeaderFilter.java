package com.eric.gateway.filter.impl;

import com.eric.gateway.filter.HttpRequestFilter;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddHeaderFilter implements HttpRequestFilter {
    @Override
    public boolean filter(final FullHttpRequest fullRequest) {
        fullRequest.headers().set("nio:", "eric");
        return true;
    }
}
