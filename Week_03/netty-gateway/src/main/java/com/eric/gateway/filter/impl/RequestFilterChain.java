package com.eric.gateway.filter.impl;

import com.eric.gateway.filter.HttpRequestFilter;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class RequestFilterChain implements HttpRequestFilter {
    private List<HttpRequestFilter> filters;

    public RequestFilterChain() {
        filters = new ArrayList<>();
    }

    public RequestFilterChain add(HttpRequestFilter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public boolean filter(FullHttpRequest fullRequest) {
        for(HttpRequestFilter requestFilter : filters) {
            if(!requestFilter.filter(fullRequest)) {
                log.error("requestFilter failed to filter the fullRequest");
                return false;
            }
        }
        log.info("Success, all filters passed!");
        return true;
    }
}
