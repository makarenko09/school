package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    public Student getStudent(long id) {
        if (studentsMap.get(id) != null) {
        return studentsMap.get(id);
        } else {
            throw new NoSuchQuestionException(" - " + id + " does not exist");
        }
    }

    public Student updateStudent(Student student) {
        Student someObj = studentsMap.values().stream().filter(obj -> Objects.equals(obj.getId(), student.getId())).map(obj -> obj.newObject(student.getId(), student.getName(), student.getAge())).findFirst().orElseThrow(() -> new NoSuchQuestionException(" - " + student + " does not exist"));
        studentsMap.put(student.getId(), someObj);
        return studentsMap.get(student.getId());
    }

    public Student deleteStudent(long id) {
        if (studentsMap.get(id) != null) {
            return studentsMap.remove(id);
        } else {
            throw new NoSuchQuestionException(" - " + id + " does not exist");
        }
    }

    public Collection<Student> getAllStudents() {
        return studentsMap.values();
    }

    public Collection<Student> getStudentsWithValueAge(int age) {
        return studentsMap.values().stream()
                .filter(obj -> obj.getAge() == age)
                .collect(Collectors.toList());
    }
}
