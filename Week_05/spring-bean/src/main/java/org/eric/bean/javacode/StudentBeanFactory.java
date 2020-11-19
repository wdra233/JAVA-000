package org.eric.bean.javacode;

import org.eric.bean.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class StudentBeanFactory {

    @Bean(name = "java-code-student")
    @Scope(value = "prototype")
    public Student student(int id, String name, String address) {
        return new Student(id, name, address);
    }
}
