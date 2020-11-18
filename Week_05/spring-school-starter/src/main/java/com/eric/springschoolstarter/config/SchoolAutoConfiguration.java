package com.eric.springschoolstarter.config;

import com.eric.springschoolstarter.bean.Klass;
import com.eric.springschoolstarter.bean.School;
import com.eric.springschoolstarter.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties(SchoolConfiguration.class)
public class SchoolAutoConfiguration {
    @Autowired
    private SchoolConfiguration schoolConfiguration;


    @Bean
    public School getSchool() {
        List<Student> students = schoolConfiguration.getStudents();
        List<Klass> klasses = schoolConfiguration.getKlasses();
        for(Klass klass : klasses) {
            for(Student student : students) {
                if(klass.getId() == student.getKlassId()) klass.addStudent(student);
            }
        }
        return new School(klasses);
    }
}
