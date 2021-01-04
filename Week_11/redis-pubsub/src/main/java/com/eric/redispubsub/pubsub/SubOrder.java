package com.eric.redispubsub.pubsub;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

@Slf4j
public class SubOrder {
    private static final String END_SIGNAL = "";

    public SubOrder(final JedisPool jedisPool, final String channelName) {
        new Thread(() -> {
            log.info("Start to subscribe order from thread {}", Thread.currentThread().getName());
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                jedis.subscribe(initPubSub(), channelName);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }).start();
    }


    private JedisPubSub initPubSub() {
        return new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                if (message.equals(END_SIGNAL)) {
                    log.info("SubPub End");
                    System.exit(0);
                }
                log.info("Receive message {} from channel {}", message, channel);
            }
        };
    }
}
