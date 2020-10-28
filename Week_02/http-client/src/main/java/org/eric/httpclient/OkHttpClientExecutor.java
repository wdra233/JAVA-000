package org.eric.httpclient;


import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class OkHttpClientExecutor {
    private static final OkHttpClient httpClient = new OkHttpClient();

    public static void sendGetRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response headers
            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            // Get response body
            System.out.println(response.body().string());
        }
    }

    public static void sendJSONPostRequest(String url, Object payload) throws IOException {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody
                .create(gson.toJson(payload), MediaType.parse("application/json; charset=utf-8"));

        System.out.println("json format: " + gson.toJson(payload));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            // Get messages
            System.out.println(response.code());

            // Get response body
            System.out.println(response.body().string());

        }

    }
}
