package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public Collection<Student> getStudentsWithAgeBetween(@RequestParam("min") int min, @RequestParam("max") int max) {
        return studentService.getStudentsAgeBetween(min, max);
    }

    @GetMapping("/get/faculty/{idStudent}")
    public ResponseEntity<Faculty> getFacultyByStudent(@PathVariable Long idStudent) {
        return ResponseEntity.ok(studentService.getStudent(idStudent).getFaculty());
    }

    @GetMapping("/get/many/{amount}")
    public Collection<Student> getStudentsWithAge(@PathVariable int amount) {
        return studentService.getStudentsWithValueAge(amount);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @PostMapping("/create/many")
    public List<Student> addStudents(@RequestBody List<Student> students) {
        return studentService.createStudents(students);
    }

    @PutMapping(path = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("/delete/{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}

