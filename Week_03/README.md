# Week03作业 -- 实现简单的网关

## 网关请求地址 http://localhost:8888/api/hello

### step01
开启本地三个gateway-servers
```$java
java -jar gateway-server-0.0.1-SNAPSHOT.jar (port 8088 by default)
java -jar gateway-server-0.0.1-SNAPSHOT.jar --server.port=8081
java -jar gateway-server-0.0.1-SNAPSHOT.jar --server.port=8082
```
### step02
test results using curl
```$java
# curl http://localhost:8888/api/hello
Hello World from: http://localhost:8082
# curl http://localhost:8888/api/hello
Hello World from: http://localhost:8081
# curl http://localhost:8888/api/hello
Hello World from: http://localhost:8088
```
通过random router实现最简单的负载均衡
