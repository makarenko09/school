package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    @PostMapping("/create")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @PostMapping("/create/many")
    public Collection<Faculty> addFaculties(@RequestBody List<Faculty> faculties) {
        return facultyService.createFaculties(faculties);
    }

    @PostMapping("/add/students/{facultyId}")
    public ResponseEntity<Void> addStudentsToFaculty(@PathVariable Long facultyId, @RequestBody List<Student> students) {
        facultyService.putStudentsToFaculty(facultyId, students);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyService.getFaculty(id);
    }

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/get/many/{color}")
    public Collection<Faculty> getFacultiesByColor(@PathVariable String color) {
        return facultyService.getFacultiesWithValueColor(color);
    }

    @GetMapping("/get/students/{facultyId}")
    public ResponseEntity<Collection<Student>> getStudentsOfFaculty(@PathVariable Long facultyId) {
        Collection<Student> students = facultyService.getStudentsByFaculty(facultyId);
        return ResponseEntity.ok(students);
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
