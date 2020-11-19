package org.eric.bean.annotation.dao;

import org.eric.bean.bean.Student;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class StudentDaoImpl implements StudentDao {
    @Override
    public List<Student> listStudents() {
        // mock all students from db
        List<Student> students =
                Arrays.asList(new Student(1, "eric", "Lake Ave"), new Student(2, "jack", "South Ave"));
        return students;
    }
}
