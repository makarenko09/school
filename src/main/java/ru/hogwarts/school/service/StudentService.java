package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.valueOf;

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

    public Integer getCountOfStudentByName(){
            return repository.getCountOfStudentsByName();
    }

    public Integer getAverageAgeOfStudentsByAge(){
            return repository.getAverageAgeOfStudentsByAge();
    }

    public List<Student> getFiveLateStudentsById(){
            return repository.getFiveLateStudentsById();
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

    public Collection<Student> getStudentsWithSomeSet() {
        List<Student> collect = getAllStudents().stream()
                .parallel()
                .filter(i -> i.getName().matches("^S.*"))
                .peek(i -> i.setName(i.getName().toUpperCase()))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
        return collect;
    }

    public Integer getAverageAgeOfAllStudents() {
        double average = getAllStudents().stream()
                .parallel()
                .map(i -> i.getAge())
                .mapToInt(Integer::intValue)
                .summaryStatistics()
                .getAverage();
        return (Integer) (int) average;
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