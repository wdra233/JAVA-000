package com.eric.redislock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {

    private static final String COUNTER_KEY = "counter";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/count_down")
    public String countDown() {
        String res = stringRedisTemplate.opsForValue().get(COUNTER_KEY);
        int cnt = res == null ? 0 : Integer.parseInt(res);

        if (cnt > 0) {
            stringRedisTemplate.opsForValue().set(COUNTER_KEY, String.valueOf(cnt - 1));
            return "Current number is: " + (cnt);
        } else {
            return "Count down to zero, cannot be count down";
        }
    }
}
