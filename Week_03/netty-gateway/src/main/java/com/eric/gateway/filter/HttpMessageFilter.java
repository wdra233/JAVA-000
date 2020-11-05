package com.eric.gateway.filter;

import io.netty.handler.codec.http.FullHttpMessage;

public interface HttpMessageFilter {
    
    boolean filter(final FullHttpMessage msg);
    
}
