package com.eric.rw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eric.rw.dao")
public class RwSeperationV2Application {

    public static void main(String[] args) {
        SpringApplication.run(RwSeperationV2Application.class, args);
    }

}
