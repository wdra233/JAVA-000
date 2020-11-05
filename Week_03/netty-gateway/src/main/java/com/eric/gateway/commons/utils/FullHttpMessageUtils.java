package com.eric.gateway.commons.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;

public class FullHttpMessageUtils {
    public static Map<String, String> extractHeadersFrom(FullHttpRequest fullHttpRequest) {
        Map<String, String> headerMap = new HashMap<>();
        HttpHeaders headers = fullHttpRequest.headers();
        Iterator<Map.Entry<String, String>> it = headers.iteratorAsString();
        while (it.hasNext()) {
            Map.Entry<String, String> header = it.next();
            headerMap.put(header.getKey(), header.getValue());
        }
        return headerMap;
    }

    public static FullHttpMessage replaceBody(String body, FullHttpMessage fullHttpMessage) {
        if (body != null) {
            ByteBuf buf = Unpooled.copiedBuffer(body, CharsetUtil.UTF_8);
            fullHttpMessage = fullHttpMessage.replace(buf);
            fullHttpMessage.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.writerIndex());
        } else {
            fullHttpMessage.headers().set(HttpHeaderNames.CONTENT_LENGTH, 0);
        }
        fullHttpMessage.headers().set(HttpHeaderNames.CONTENT_TYPE, APPLICATION_JSON);
        return fullHttpMessage;
    }
}
