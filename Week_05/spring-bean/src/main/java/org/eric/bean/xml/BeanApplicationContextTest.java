package org.eric.bean.xml;

import org.eric.bean.bean.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanApplicationContextTest {
    private static final String CONFIG_PATH = "applicationContext.xml";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_PATH);
        Student studentById = (Student) context.getBean("student1");
        Student eric = (Student) context.getBean("eric");
        Student jack = (Student) context.getBean("jack");
        System.out.println(studentById);
        System.out.println(eric);
        System.out.println(jack);

        // Student(id=1, name=Eric, address=South Ave)
        // Student(id=1, name=Eric, address=South Ave)
        // Student(id=2, name=Jack, address=Lake Ave)
    }
}
