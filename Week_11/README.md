# Week11 必做作业
### 必做1: 在 Java 中实现一个简单的分布式锁, 在 Java 中实现一个分布式计数器，模拟减库存。
作业地址: [redis-lock](redis-lock)
- 加锁: 使用redis setnx加锁并设置超时
```shell script
Boolean flg = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, threadInfo, 10L, TimeUnit.SECONDS);
```
- 解锁1: 使用lua脚本解锁
```shell script
if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return 0
end
```

- 解锁2: 可使用redis watch <key>, multi命令来模拟lua脚本

- 也可以使用Redisson 分布式加锁解锁，使用起来更加方便。而且解决了缓存续期的问题
```shell script
RLock lock = redisson.getLock("myLock");
lock.lock();
// business logic
lock.unlock();
```

#### 测试
1. 首先设置redis库存为100 `set counter 100`
2. 然后使用`sb -u "http://localhost:8080/count_down" -c 4 -N 60`进行压测, 观察是否会超卖
3. redis 命令: `get counter`为0, 说明redis作为分布式锁的可行性

### 必做2: 基于 Redis 的 PubSub 实现订单异步处理
作业地址: [redis-pubsub](redis-pubsub)
- 一共两个class: SubOrder和PubOrder
- SubOrder class 订阅订单
```shell script
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
```
- PubOrder class 发布订单
```shell script
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
```
- demo
```shell script
public class Demo {
    public static void main(String[] args) {
        JedisPool jedisPool = JedisPoolUtil.getJedisPool();
        final String channelName = "order";

        SubOrder subOrder = new SubOrder(jedisPool, channelName);
        PubOrder.pubOrder(jedisPool, channelName);
    }
}
```

