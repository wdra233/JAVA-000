package com.eric.springschoolstarter.config;

import com.eric.springschoolstarter.bean.Klass;
import com.eric.springschoolstarter.bean.Student;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "school")
public class SchoolConfiguration {
    private List<Student> students;
    private List<Klass> klasses;
}
