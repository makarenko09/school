package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Collection<Student> getAllStudents() {
        return studentsMap.values();
    }

    public Collection<Student> getStudentsWithValueAge(int age) {
        return studentsMap.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
