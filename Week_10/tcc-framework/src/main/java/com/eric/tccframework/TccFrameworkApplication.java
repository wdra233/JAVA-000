package com.eric.tccframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TccFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccFrameworkApplication.class, args);
    }

}
