package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;
@Service
public class StudentService {
    private final Map<Long, Student> studentsMap = new HashMap<>();
    private long lastId = 0;

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

    public Student deleteStudent(long id) {
        return studentsMap.remove(id);
    }
}
