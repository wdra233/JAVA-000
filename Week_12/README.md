# Week12 必做作业

## 必做1 redis主从复制, sentinel高可用
作业1: [redis-ms](redis-ms)
使用docker-compose进行配置
- redis-master(redis7000.conf)
```shell script
bind 0.0.0.0
protected-mode no
port 7000
tcp-backlog 511
timeout 0
tcp-keepalive 300
daemonize no
supervised no
pidfile /var/run/redis_7000.pid
loglevel notice
logfile ""
databases 16
always-show-logo yes
save 900 1
save 300 10
save 60 10000
stop-writes-on-bgsave-error yes
rdbcompression yes
rdbchecksum yes
dbfilename dump.rdb
dir ./
replica-serve-stale-data yes
replica-read-only yes
repl-diskless-sync no
repl-diskless-sync-delay 5
repl-disable-tcp-nodelay no
replica-priority 100
lazyfree-lazy-eviction no
lazyfree-lazy-expire no
lazyfree-lazy-server-del no
replica-lazy-flush no
appendonly no
appendfilename "appendonly.aof"
appendfsync everysec
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
aof-load-truncated yes
aof-use-rdb-preamble yes
lua-time-limit 5000
slowlog-log-slower-than 10000
slowlog-max-len 128
latency-monitor-threshold 0
notify-keyspace-events ""
hash-max-ziplist-entries 512
hash-max-ziplist-value 64
list-max-ziplist-size -2
list-compress-depth 0
set-max-intset-entries 512
zset-max-ziplist-entries 128
zset-max-ziplist-value 64
hll-sparse-max-bytes 3000
stream-node-max-bytes 4096
stream-node-max-entries 100
activerehashing yes
client-output-buffer-limit normal 0 0 0
client-output-buffer-limit replica 256mb 64mb 60
client-output-buffer-limit pubsub 32mb 8mb 60
hz 10
dynamic-hz yes
aof-rewrite-incremental-fsync yes
rdb-save-incremental-fsync yes
```

- redis-slave(redis7001.conf, redis7002.conf)
```shell script
bind 0.0.0.0
protected-mode no
port 7001
tcp-backlog 511
timeout 0
tcp-keepalive 300
daemonize no
supervised no
pidfile /var/run/redis_7001.pid
loglevel notice
logfile ""
databases 16
always-show-logo yes
save 900 1
save 300 10
save 60 10000
stop-writes-on-bgsave-error yes
rdbcompression yes
rdbchecksum yes
dbfilename dump.rdb
dir ./
replica-serve-stale-data yes
replica-read-only yes
repl-diskless-sync no
repl-diskless-sync-delay 5
repl-disable-tcp-nodelay no
replica-priority 100
lazyfree-lazy-eviction no
lazyfree-lazy-expire no
lazyfree-lazy-server-del no
replica-lazy-flush no
appendonly no
appendfilename "appendonly.aof"
appendfsync everysec
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
aof-load-truncated yes
aof-use-rdb-preamble yes
lua-time-limit 5000
slowlog-log-slower-than 10000
slowlog-max-len 128
latency-monitor-threshold 0
notify-keyspace-events ""
hash-max-ziplist-entries 512
hash-max-ziplist-value 64
list-max-ziplist-size -2
list-compress-depth 0
set-max-intset-entries 512
zset-max-ziplist-entries 128
zset-max-ziplist-value 64
hll-sparse-max-bytes 3000
stream-node-max-bytes 4096
stream-node-max-entries 100
activerehashing yes
client-output-buffer-limit normal 0 0 0
client-output-buffer-limit replica 256mb 64mb 60
client-output-buffer-limit pubsub 32mb 8mb 60
hz 10
dynamic-hz yes
aof-rewrite-incremental-fsync yes
rdb-save-incremental-fsync yes
slaveof 127.0.0.1 7000
```

- redis-sentinel(sentinel.conf)
```shell script
port 26379
sentinel monitor mymaster 127.0.0.1 7000 1
```
- docker-compose.yml
```yaml
version: '3'
services:
  master:
    image: redis
    container_name: redis-master
    ports:
      - 7000:7000
    volumes:
      - ./redis7000.conf:/usr/local/etc/redis/redis.conf
    command: redis-server /usr/local/etc/redis/redis.conf
    network_mode: host

  slave1:
    image: redis
    container_name: redis-slave1
    ports:
      - 7001:7001
    volumes:
      - ./redis7001.conf:/usr/local/etc/redis/redis.conf
    command: redis-server /usr/local/etc/redis/redis.conf
    network_mode: host

  slave2:
    image: redis
    container_name: redis-slave2
    ports:
      - 7002:7002
    volumes:
      - ./redis7002.conf:/usr/local/etc/redis/redis.conf
    command: redis-server /usr/local/etc/redis/redis.conf
    network_mode: host

  sentinel:
    image: redis
    container_name: sentinel
    ports:
      - 26379:26379
    volumes:
      - ./sentinel.conf:/usr/local/etc/redis/sentinel.conf
    command: redis-sentinel /usr/local/etc/redis/sentinel.conf
    network_mode: host
```
### config steps
1. `docker-compose up -d` 启动dockers
2. `docker exec -it redis-master /bin/bash`进入redis-master
3. `/usr/local/bin/redis-cli -p 7000` 使用redis-cli进入master节点
4. `info replication` 获取主从信息如下:
```shell script
# Replication
role:master
connected_slaves:2
slave0:ip=127.0.0.1,port=7001,state=online,offset=974431,lag=0
slave1:ip=127.0.0.1,port=7002,state=online,offset=974431,lag=0
master_replid:e1f0a760d340ebc99a8865f50383b3a6cc2dc3df
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:974431
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:974431
```
5. `set k1 v1` 在主节点设置key-value
6. 在从节点`get k1`, 可以得到对应`v1`
7. 通过`docker stop redis-master`手动stop主节点, 主节点此时切换到redis-slave2

## redis-cluster 配置
作业2: [redis-cluster](redis-cluster) 使用docker-compose配置
- redis nodes配置文件，三主三从, 一共配置6个节点. 配置文件以redis6380.conf为例, 其他5个类似
```shell script
bind 0.0.0.0
protected-mode no
port 6380
tcp-backlog 511
timeout 0
tcp-keepalive 300
daemonize no
supervised no
pidfile /var/run/redis_6380.pid
loglevel notice
logfile ""
databases 16
always-show-logo yes
save 900 1
save 300 10
save 60 10000
stop-writes-on-bgsave-error yes
rdbcompression yes
rdbchecksum yes
dbfilename dump.rdb
dir ./
replica-serve-stale-data yes
replica-read-only yes
repl-diskless-sync no
repl-diskless-sync-delay 5
repl-disable-tcp-nodelay no
replica-priority 100
lazyfree-lazy-eviction no
lazyfree-lazy-expire no
lazyfree-lazy-server-del no
replica-lazy-flush no
appendonly no
appendfilename "appendonly.aof"
appendfsync everysec
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
aof-load-truncated yes
aof-use-rdb-preamble yes
lua-time-limit 5000
slowlog-log-slower-than 10000
slowlog-max-len 128
latency-monitor-threshold 0
notify-keyspace-events ""
hash-max-ziplist-entries 512
hash-max-ziplist-value 64
list-max-ziplist-size -2
list-compress-depth 0
set-max-intset-entries 512
zset-max-ziplist-entries 128
zset-max-ziplist-value 64
hll-sparse-max-bytes 3000
stream-node-max-bytes 4096
stream-node-max-entries 100
activerehashing yes
client-output-buffer-limit normal 0 0 0
client-output-buffer-limit replica 256mb 64mb 60
client-output-buffer-limit pubsub 32mb 8mb 60
hz 10
dynamic-hz yes
aof-rewrite-incremental-fsync yes
rdb-save-incremental-fsync yes
cluster-enabled yes
cluster-config-file nodes-6380.conf
cluster-require-full-coverage no
```
> Note: 注意要在config文件中加入和cluster相关配置, eg. **cluster-enabled** yes, **cluster-config-file** nodes-6380.conf, 
> **cluster-require-full-coverage** no

- docker-compose.yml file
```yaml
version: '3'
services:
  redis6380:
    image: redis
    container_name: redis6380
    ports:
      - 6380:6380
    volumes:
      - ./redis6380.conf:/usr/local/etc/redis/redis.conf
    command:
      redis-server /usr/local/etc/redis/redis.conf
    network_mode: host

  redis6381:
    image: redis
    container_name: redis6381
    ports:
      - 6381:6381
    volumes:
      - ./redis6381.conf:/usr/local/etc/redis/redis.conf
    command:
      redis-server /usr/local/etc/redis/redis.conf
    network_mode: host

  redis6382:
    image: redis
    container_name: redis6382
    ports:
      - 6382:6382
    volumes:
      - ./redis6382.conf:/usr/local/etc/redis/redis.conf
    command:
      redis-server /usr/local/etc/redis/redis.conf
    network_mode: host


  redis6383:
    image: redis
    container_name: redis6383
    ports:
      - 6383:6383
    volumes:
      - ./redis6383.conf:/usr/local/etc/redis/redis.conf
    command:
      redis-server /usr/local/etc/redis/redis.conf
    network_mode: host

  redis6384:
    image: redis
    container_name: redis6384
    ports:
      - 6384:6384
    volumes:
      - ./redis6384.conf:/usr/local/etc/redis/redis.conf
    command:
      redis-server /usr/local/etc/redis/redis.conf
    network_mode: host

  redis6385:
    image: redis
    container_name: redis6385
    ports:
      - 6385:6385
    volumes:
      - ./redis6385.conf:/usr/local/etc/redis/redis.conf
    command:
      redis-server /usr/local/etc/redis/redis.conf
    network_mode: host
```

### Config Steps
1. `docker-compose up -d` 启动dockers
2. `docker exec -it redis6380 /bin/bash`进入其中一个redis节点
3. `cd /usr/local/bin`
3. 创建redis集群 
`redis-cli --cluster create 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6384 127.0.0.1:6385 --cluster-replicas 1`
4. 使用`redis-cli -p 6380 -c`连接其中一个redis server
> 连接时注意加上 -c 表明以cluster形式连接
5. 使用`cluster nodes`查看集群信息如下:
```shell script
aeaa6223198f2c84379cb3280cd702930075aff4 127.0.0.1:6382@16382 master - 0 1609979160121 3 connected 10923-16383
7b31382832ed0cd7d43f1bfec869c301a22c569f 127.0.0.1:6380@16380 myself,master - 0 1609979158000 1 connected 0-5460
710670f48b9d12f8002e3823725d3704c63b15bf 127.0.0.1:6381@16381 master - 0 1609979161125 2 connected 5461-10922
3bda520dc85d4a44d8e02acd9ee4f9160a33f834 127.0.0.1:6384@16384 slave 710670f48b9d12f8002e3823725d3704c63b15bf 0 1609979159000 2 connected
2bd3cae3d9cf744b62ba18e71e1a732b0daf91bc 127.0.0.1:6383@16383 slave 7b31382832ed0cd7d43f1bfec869c301a22c569f 0 1609979160000 1 connected
577c871fa4826476603a6dee73db791fcbba58d7 127.0.0.1:6385@16385 slave aeaa6223198f2c84379cb3280cd702930075aff4 0 1609979159117 3 connected
```
6. 使用`cluster info`查看集群信息如下:
```shell script
cluster_state:ok
cluster_slots_assigned:16384
cluster_slots_ok:16384
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:6
cluster_size:3
cluster_current_epoch:6
cluster_my_epoch:1
cluster_stats_messages_ping_sent:21531
cluster_stats_messages_pong_sent:20659
cluster_stats_messages_sent:42190
cluster_stats_messages_ping_received:20654
cluster_stats_messages_pong_received:21531
cluster_stats_messages_meet_received:5
cluster_stats_messages_received:42190
```
从以上信息可以看出redis6380(master)->redis6383(slave), redis6381(master)->redis6384(slave), redis6382(master)->redis6385(slave).
7. redis命令: `set k1 v1`, 得到以下结果:
```shell script
127.0.0.1:6380> set k1 v1
-> Redirected to slot [12706] located at 127.0.0.1:6382
OK
127.0.0.1:6382>get k1
"v1"
```
以上说明在redis6380节点添加k1-v1, k1会被插在slot 12706上, 即在redis6382节点上
