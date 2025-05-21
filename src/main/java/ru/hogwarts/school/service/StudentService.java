package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;
    private final LocalValidatorFactoryBean localValidatorFactoryBean;

    public StudentService(StudentRepository repository, LocalValidatorFactoryBean localValidatorFactoryBean) {
        this.repository = repository;
        this.localValidatorFactoryBean = localValidatorFactoryBean;
    }

    public Student createStudent(Student student) {
        localValidateUnique(); //TODO - add localValidateUnique()
        return repository.save(student);
    }

    private void localValidateUnique() {
        //TODO - add localValidateUnique()
    }

    public List<Student> createStudents(List<Student> students) {

        return repository.saveAll(students);
    }

    public Collection<Student> getStudentsAgeBetween(short min, short max) {
    return repository.findByAgeBetween(min, max);
}
    public Student getStudent(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchSomeObjectException(" - " + id + " does not exist"));
    }

    public Student updateStudent(Student student) {
        if (!repository.existsById(student.getId())) {
            throw new NoSuchSomeObjectException(" - " + student + " does not exist");
        }
        return repository.save(student);
    }

    public Student deleteStudent(Long id) {
        Student objDeleted = getStudent(id);
        repository.delete(objDeleted);
        return objDeleted;
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
