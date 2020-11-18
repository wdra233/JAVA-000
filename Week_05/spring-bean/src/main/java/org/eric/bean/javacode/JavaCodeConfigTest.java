package org.eric.bean.javacode;

import org.eric.bean.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaCodeConfigTest {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(StudentBeanFactory.class);
    Student student = (Student) context.getBean("java-code-student", 1, "eric", "Lake Ave");
    System.out.println(student);
  }
}
