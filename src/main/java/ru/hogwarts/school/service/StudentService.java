package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public Collection<Student> createStudents(List<Student> students) {
        return repository.saveAll(students);
    }

    public Student getStudent(Long id) {
        logger.info(" - try get student with id = {}", id);
        Student student = repository.findById(id).orElseThrow(() -> new NoSuchObjectException(" - " + id + " does not exist"));
        logger.info(" - this student with id '{}', is '{}'", id, student);
        return student;
    }

    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }

    public Collection<Student> getStudentsWithValueAge(int age) {
        return repository.findAll().stream()
                .filter(obj -> obj.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> getStudentsWithValuesAge(int min, int max) {
    return repository.findByAgeBetween(min, max);
}

    public Student updateStudent(Student student) {
        if (!repository.existsById(student.getId())) {
            throw new NoSuchObjectException(" - " + student + " does not exist");
        }
        return repository.save(student);
    }

    public Student deleteStudent(Long id) {
        Student objDeleted = getStudent(id);
        repository.delete(objDeleted);
        return objDeleted;
    }

}
