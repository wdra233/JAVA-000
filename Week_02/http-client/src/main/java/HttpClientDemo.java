import org.eric.httpclient.ApacheHttpClient;
import org.eric.httpclient.OkHttpClientExecutor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientDemo {
    public static final String URL = "https://www.baidu.com";
    public static void main(String[] args) throws IOException {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "eric");

        ApacheHttpClient.sendGetRequestToURL(URL);

        ApacheHttpClient.sendJSONPostRequest(URL, payload);
        OkHttpClientExecutor.sendGetRequest(URL);
        OkHttpClientExecutor.sendJSONPostRequest(URL, payload);
    }

}
