package com.eric.gateway.filter.impl;

import com.eric.gateway.filter.HttpMessageFilter;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class MessageFilterChain implements HttpMessageFilter {
    private List<HttpMessageFilter> filters;

    public MessageFilterChain() {
        filters = new ArrayList<>();
    }

    public MessageFilterChain add(HttpMessageFilter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public boolean filter(FullHttpMessage msg) {
        for(HttpMessageFilter requestFilter : filters) {
            if(!requestFilter.filter(msg)) {
                log.error("httpFilter failed!");
                return false;
            }
        }
        log.info("Success, all filters passed!");
        return true;
    }

}
