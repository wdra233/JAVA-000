package io.kimmking.rpcfx.client;

import io.kimmking.rpcfx.api.RpcfxException;
import io.kimmking.rpcfx.constants.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpClientPool {

    private CloseableHttpClient httpClient;

    private HttpClientPool() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(AppConstants.MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(AppConstants.MAX_PER_ROUTE);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(AppConstants.CONN_TIMEOUT)
                .setConnectTimeout(AppConstants.CONN_TIMEOUT)
                .setSocketTimeout(AppConstants.CONN_TIMEOUT).build();

        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public static HttpClientPool getInstance() {
        return Singleton.pool;
    }

    public String post(final String json, final String url) throws RpcfxException {
        final HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
        StringEntity requestEntity = new StringEntity(json, StandardCharsets.UTF_8);
        httpPost.setEntity(requestEntity);
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            log.error("httpClient request error", e);
            throw new RpcfxException(e);
        }
    }

    private static class Singleton {
        private static HttpClientPool pool = new HttpClientPool();
    }
}
