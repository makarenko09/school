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

    public Collection<Student> getStudentsAgeBetween(short min, short max) {
    return repository.findByAgeBetween(min, max);
}
    public Student getStudent(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchObjectException(" - " + id + " does not exist"));
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


    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }

    public Collection<Student> getStudentsWithValueAge(int age) {
        return repository.findAll().stream()
                .filter(obj -> obj.getAge() == age)
                .collect(Collectors.toList());
    }

}
