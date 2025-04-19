package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public List<Student> createStudents(List<Student> students) {
        return repository.saveAll(students);
    }

    public Student getStudent(Long id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new NoSuchSomeObjectException(" - " + id + " does not exist");
        }
    }

    public Student updateStudent(Student student) {
        if (repository.findById(student.getId()).isPresent()) {
            return repository.save(student);
        } else {
            throw new NoSuchSomeObjectException(" - " + student + " does not exist");
        }
    }

    public void deleteStudent(Long id) {
        Student objDeleted = getStudent(id);
        repository.delete(getStudent(id));
    }

    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }

    public Collection<Student> getStudentsWithValueAge(int age) {
        return repository.findAll().stream()
                .filter(obj -> obj.getAge() == age)
                .collect(Collectors.toList());
    }
}
