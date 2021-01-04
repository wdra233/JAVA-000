package com.eric.redispubsub;

import com.eric.redispubsub.pubsub.PubOrder;
import com.eric.redispubsub.pubsub.SubOrder;
import com.eric.redispubsub.util.JedisPoolUtil;
import redis.clients.jedis.JedisPool;

public class Demo {
    public static void main(String[] args) {
        JedisPool jedisPool = JedisPoolUtil.getJedisPool();
        final String channelName = "order";

        SubOrder subOrder = new SubOrder(jedisPool, channelName);
        PubOrder.pubOrder(jedisPool, channelName);
    }
}
