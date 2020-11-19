package org.eric.bean.annotation;

import org.eric.bean.annotation.service.StudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationConfigTest {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentService studentService = ctx.getBean(StudentService.class);
        System.out.println(studentService.queryAllStudents());

        // [Student(id=1, name=eric, address=Lake Ave), Student(id=2, name=jack, address=South Ave)]
    }
}
