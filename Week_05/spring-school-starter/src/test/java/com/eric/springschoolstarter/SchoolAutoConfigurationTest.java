package com.eric.springschoolstarter;

import com.eric.springschoolstarter.bean.Klass;
import com.eric.springschoolstarter.bean.School;
import com.eric.springschoolstarter.bean.Student;
import com.eric.springschoolstarter.config.SchoolAutoConfiguration;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SchoolAutoConfiguration.class)
class SchoolAutoConfigurationTest {

    @Autowired
    private School school;

    @Test
    public void testSchoolAutoWiredOrNot() {
        School expectedSchool = generateMockTestData();
        Assert.assertEquals(expectedSchool, school);
    }

    private School generateMockTestData() {
        Student s1 = new Student(1, 1, "Eric");
        Student s2 = new Student(2, 1, "Jack");
        Student s3 = new Student(3, 2, "Alice");
        Klass klass1 = new Klass(1, "klass01");
        Klass klass2 = new Klass(2, "klass02");
        klass1.addStudent(s1).addStudent(s2);
        klass2.addStudent(s3);
        return new School(Arrays.asList(klass1, klass2));
    }
}