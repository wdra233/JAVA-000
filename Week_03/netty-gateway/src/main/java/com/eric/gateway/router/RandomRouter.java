package com.eric.gateway.router;

import java.util.List;
import java.util.Random;

public class RandomRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        Random rnd = new Random();
        int index = rnd.nextInt(endpoints.size());
        return endpoints.get(index);
    }
}
