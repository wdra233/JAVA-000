package com.eric.redispubsub.pubsub;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

@Slf4j
public class PubOrder {
    private static final String END_SIGNAL = "";

    public static void pubOrder(final JedisPool jedisPool, String channelName) {
        try (Jedis jedis = jedisPool.getResource()) {
            for(int i = 0; i < 10; i++) {
                String msg = UUID.randomUUID().toString() + System.currentTimeMillis();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jedis.publish(channelName, msg);
                log.info("-------------Message {} published!------------------", msg);
            }
            jedis.publish(channelName, END_SIGNAL);
            log.info("---------Publish stopped!!!-----------");
        }
    }
}
