package org.eric.httpclient;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ApacheHttpClient {

    public static void sendGetRequestToURL(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(15000)
                .build());

        // close with try-with resource
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {

            // Get httpResponse status
            System.out.println(response.getStatusLine().toString());

            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }
    }

    public static void sendJSONPostRequest(String url, Object payload) throws IOException {
        HttpPost post = new HttpPost(url);
        Gson gson = new Gson();

        // send a JSON data
        post.setEntity(new StringEntity(gson.toJson(payload)));
        System.out.println("json format: " + gson.toJson(payload));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(response.getStatusLine().toString());
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }
    }
}
