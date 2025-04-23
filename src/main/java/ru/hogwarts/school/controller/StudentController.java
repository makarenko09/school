package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/get/many")
    public Collection<Student> getStudentsWithAgeBetween(@RequestParam("min") short min, @RequestParam("max") short max) {
        return studentService.getStudentsAgeBetween(min, max);
    }

    @GetMapping("/get/faculty/{id}")
    public Faculty getStudentsWithFaculty(@PathVariable Long id) {
        return studentService.getStudent(id).getFaculty();
    }

    @GetMapping("/get/many/{amount}")
    public Collection<Student> getStudentsWithAge(@PathVariable int amount) {
        return studentService.getStudentsWithValueAge(amount);
    }
    @GetMapping("/get/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PostMapping("/create")
    public Student addStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PostMapping("/create/many")
    public List<Student> addStudents(@RequestBody List<Student> students) {
        return studentService.createStudents(students);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/del/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}

