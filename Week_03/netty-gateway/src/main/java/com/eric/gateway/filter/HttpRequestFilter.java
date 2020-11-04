package com.eric.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpRequestFilter {
    
    boolean filter(final FullHttpRequest fullRequest);
    
}
