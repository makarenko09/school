package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/someStudents/{amount}")
    public Collection<Student> getStudentsWithAge(@PathVariable int amount) {
        return studentService.getStudentsWithValueAge(amount);
    }
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {

        return studentService.getStudent(id);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }
//    @PutMapping
//    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
//        Student someObject = studentService.updateStudent(student);
//        if (someObject == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(someObject);
//    }
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }


}
