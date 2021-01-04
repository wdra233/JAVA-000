package com.eric.redispubsub.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
    private final JedisPool jedisPool;

    private JedisPoolUtil() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(200);
        poolConfig.setMaxIdle(32);
        poolConfig.setMaxWaitMillis(100 * 1000);
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(poolConfig, "192.168.56.10", 6380, 60000);
    }

    public static JedisPool getJedisPool() {
        return Singleton.util.jedisPool;
    }

    private static class Singleton {
        private static JedisPoolUtil util = new JedisPoolUtil();
    }
}
