package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/get/many/{color}")
    public List<Faculty> getFacultiesWitColor(@PathVariable String color) {
        return facultyService.getFacultiesWithValueColor(color);
    }

    @GetMapping("/get/students/{facultyId}")
    public ResponseEntity<List<Student>> getStudentsByFaculty(@PathVariable Long facultyId) {
        List<Student> students = facultyService.getStudentsByFaculty(facultyId);
        return ResponseEntity.ok(students);
    }

    @PostMapping("/add/students/{facultyId}")
    public ResponseEntity<Void> addStudentsToFaculty(@PathVariable Long facultyId, @RequestBody List<Student> students) {
        facultyService.addStudentsToFaculty(facultyId, students);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyService.getFaculty(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @PostMapping("/create/many")
    public List<Faculty> addFaculties(@RequestBody List<Faculty> faculties) {
        return facultyService.createFaculties(faculties);
    }

    @PutMapping(path = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.updateFaculty(faculty));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
}
