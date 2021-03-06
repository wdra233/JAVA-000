package com.eric.rw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eric.rw.dao")
public class RwSeperationV1Application {

    public static void main(String[] args) {
        SpringApplication.run(RwSeperationV1Application.class, args);
    }

}
