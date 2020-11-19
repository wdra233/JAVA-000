package org.eric.bean.annotation.service;


import org.eric.bean.annotation.dao.StudentDaoImpl;
import org.eric.bean.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentService {

    @Autowired
    private StudentDaoImpl studentDao;

    public List<Student> queryAllStudents() {
        return studentDao.listStudents();
    }
}
