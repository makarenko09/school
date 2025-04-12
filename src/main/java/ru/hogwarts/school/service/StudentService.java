package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentService {
    private final Map<Long, Student> studentsMap = new HashMap<>();
    private Long lastId = 0L;

    public Student createStudent(Student student) {
        student.setId(++lastId);
        studentsMap.put((this.lastId), student);
        return student;
    }

    public Student getStudent(Long id) {
        return studentsMap.get(id);
    }

    public Student updateStudent(Student student) {
        studentsMap.replace(student.getId(), student);
        return student;
    }

    public Student deleteStudent(Long id) {
        return studentsMap.remove(id);
    }
}
