# Week07
## 必做1 Requirement: 
按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率
## Solution:
- [batch-write](write-optimizer)
    - [batch-insert](write-optimizer/src/main/java/com/eric/batch)
    - [load-data-infile](write-optimizer/src/main/java/com/eric/load)

results: 
- batch insert 1000000 records --> duration time 8.71s
- load data infile --> duration time 5.69s
       
## 必做2 Requirement:
读写分离 - 动态切换数据源版本 1.0
## Solution:
- [configure master-slave replication](master-slave-starter)
- [dynamically switch datasource using SpringBoot and MyBatis](rw_seperation_v1)

## 必做3 Requirement:
读写分离 - 数据库框架版本 2.0
## Solution:
- [configure shardingsphere datasource](rw_seperation_v2)
    
    
    