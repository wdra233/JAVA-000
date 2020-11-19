# Assignment 1
## Requirement: 
写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 Github。
## Solution:
- [spring-bean-configuration](spring-bean)
    - [xml-configuration](spring-bean/src/main/java/org/eric/bean/xml)
    - [java-code-configuration](spring-bean/src/main/java/org/eric/bean/javacode)
    - [annotation-configuration](spring-bean/src/main/java/org/eric/bean/annotation)

# Assignment 2
## Requirement:
给前面课程提供的 Student/Klass/School 实现自动配置和 Starter
## Solution:
- [spring-school-auto-configuration](spring-school-starter)

# Assignment 3
## Requirement:
研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
1）使用 JDBC 原生接口，实现数据库的增删改查操作。
2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。
## Solution:
- [jdbc-connectionPool](jdbc-connectionpool)
    - [jdbc-native-interface](jdbc-connectionpool/src/main/java/com/eric/nativejdbc)
    - [PrepareStatement-transaction-batch](jdbc-connectionpool/src/main/java/com/eric/preparestastatement)
    - [HikariCP](jdbc-connectionpool/src/main/java/com/eric/hikaricp)
    
    
    