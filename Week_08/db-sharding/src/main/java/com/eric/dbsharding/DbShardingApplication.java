package com.eric.dbsharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eric.dbsharding.dao")
public class DbShardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbShardingApplication.class, args);
    }

}
