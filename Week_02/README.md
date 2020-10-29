# JVM Tuning notes

## First things first

### Install SuperBenchmarker on windows

- 管理员身份打开powershell
- 运行
  Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
- 执行choco install superbenchmarker
- 输入 sb

```shell
sb -u http://localhost:8088/api/hello -c 20 -N 60
```

测得的RPS峰值是3517

![image of superbenchmarker](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\sb-peak-localhost.PNG)

![image of sb analysis](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\sb-peak-analysis.PNG)

## GC analysis

```sh
操作系统 windows10
jdk version: jdk 11.0.8
# compile
javac -g GCLogAnalysis.java
# run
java GCLogAnalysis
```

### 1. Simple demo

use  default GC for jdk 11

![image of default](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-simple.PNG)

Save GC logs to files

![image of GC logs](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-java11-cmd.PNG)

通过查看gc.simple.log日志即可获得日志相关信息

### 2. OOM

通过设置 -Xmx128m 来触发OOM

![image of OOM](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-OOM-01.PNG)

![image of OOM part2](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-OOM-02.PNG)

可以看出设置堆内存过小会导致full gc次数增加, 但是垃圾清理效果并没有增加，最终导致OOM

### 纵向对比: 分别设置堆内存512m, 1g, 2g, 4g观察GC log

```java
java -Xms512m -Xmx512m -XX:+PrintGCDetails GCLogAnalysis
```

![](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-512m-01.PNG)

![](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-512m-02.PNG)

```java
java -Xms1g -Xmx1g -XX:+PrintGCDetails GCLogAnalysis
```

![](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-1g-01.PNG)

![](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-1g-02.PNG)

```java
java -Xms2g -Xmx2g -XX:+PrintGCDetails GCLogAnalysis
```

![](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-2g-01.PNG)

```java
java -Xms4g -Xmx4g -XX:+PrintGCDetails GCLogAnalysis
```

![](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-4g-01.PNG)

### 小结

- 随着堆内存增加，单位时间内创建对象增加，效率更高，同时减少OOM发生的机率，减少full gc次数。
- 随着设置的内存不断增加，性能提升的效果不会太显著。从以上的案例中，堆内存设置从512m到1g的性能提升最高



## 横向对比 - 分别使用SerialGC, ParallelGC, CMSGC来分析GC log

```java
java -XX:+UserSerialGC -Xms512m -Xmx512m -XX:+PrintGC GCLogAnalysis
```

![](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-Serial-512m.PNG)

```java
java -XX:+UserParallelGC -Xms512m -Xmx512m -XX:+PrintGC GCLogAnalysis
```

![](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-Parallel-512m.PNG)

```java
java -XX:+UserConcMarkSweepGC -Xms512m -Xmx512m -XX:+PrintGC GCLogAnalysis
```

![](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-CMS-512m-01.PNG)

![GC-CMS-512m-02](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-CMS-512m-02.PNG)

```java
java -XX:+UserG1GC -Xms512m -Xmx512m -XX:+PrintGC GCLogAnalysis
```

!(E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-G1-51m-02.PNG)

![GC-G1-512m-01](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-G1-512m-01.PNG)

![](E:\ADVANCED JAVA TRAINING\JAVA-000\Week_02\screenshots\GC-G1-51m-02.PNG)

### 小结

- 在堆内存设置较小的情况下，SerialGC会有不错的效果。但如果内存过小，各类GC性能表现都不佳，因此尽量选择配置堆内存1g以上
- 高吞吐量考虑使用ParallelGC, 低延迟考虑CMSGC, 在内存较大情况下可考虑使用G1GC
- 经验: Xms和Xmx尽量设置一样