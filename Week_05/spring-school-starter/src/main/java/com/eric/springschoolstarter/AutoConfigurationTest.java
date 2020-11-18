package com.eric.springschoolstarter;

import com.eric.springschoolstarter.bean.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class AutoConfigurationTest implements CommandLineRunner {
    @Autowired
    private School school;

    public static void main(String[] args) {
        SpringApplication.run(AutoConfigurationTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(school.getKlasses().toString());
        // [Klass(id=1, name=klass01, students=[Student(id=1, klassId=1, name=Eric), Student(id=2, klassId=1, name=Jack)]), Klass(id=2, name=klass02, students=[Student(id=3, klassId=2, name=Alice)])]
    }
}
