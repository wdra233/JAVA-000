package com.eric.springschoolstarter.bean;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Klass {
    @NonNull
    private int id;
    @NonNull
    private String name;
    private List<Student> students = new ArrayList<>();


    public Klass addStudent(Student student) {
        students.add(student);
        return this;
    }
}
