# Week 06
## 必做
基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面 2 周的作业依然要是用到这个表结构
- [DDL SQL files](sql)

## 选做
对 MySQL 配置不同的数据库连接池（DBCP、C3P0、Druid、Hikari），测试增删改查 100 万次，对比性能，生成报告。
- 整合spring-mybatis-connectionPool
   - [spring-mybatis-integration](spring-mybatis-cp-integration)
- 不同数据库连接池的比较(本机测试)
  - C3P0 batch insert 1,000,000 records, 花费时间4837.574s
  - Hikari batch insert 1,000,000 records, 花费时间4553.78s

**Note: 由于批量插入太耗费时间，因此就只比较了C3P0和Hikari.麻烦助教老师在批改的时候看一下db.properties是否因为配置不正确导致性能过慢。
在此先提前感谢助教老师了^^**