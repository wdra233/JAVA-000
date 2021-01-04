package com.eric.redislock.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class CounterController {

    private static final String COUNTER_KEY = "counter";

    private static final String REDIS_LOCK = "redis_lock";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisScript<Integer> script;

    @GetMapping("/count_down")
    public String countDown() {
        String threadInfo = System.currentTimeMillis() + ":" + Thread.currentThread().getName();
        try {
            Boolean flg = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, threadInfo, 10L, TimeUnit.SECONDS);
            if (!flg) {
                return "Failed to get the lock";
            }
            String res = redisTemplate.opsForValue().get(COUNTER_KEY);
            int cnt = res == null ? 0 : Integer.parseInt(res);

            if (cnt > 0) {
                redisTemplate.opsForValue().set(COUNTER_KEY, String.valueOf(cnt - 1));
                log.info("Current number is {}", cnt);
                return "Current number is: " + (cnt);
            } else {
                log.info("Current number is 0");
                return "Count down to zero, cannot be count down";
            }
        }
        finally {
            Integer res = redisTemplate.execute(script, new ArrayList<>(Arrays.asList(REDIS_LOCK)), threadInfo);
            if (res.intValue() == 1) {
                log.info("delete lock success");
            } else {
                log.info("delete lock failed");
            }
        }

    }
}
