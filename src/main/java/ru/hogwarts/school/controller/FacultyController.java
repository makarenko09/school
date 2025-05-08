package ru.hogwarts.school.controller;

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

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/get/many/{color}")
    public Collection<Faculty> getFacultiesWitColor(@PathVariable String color) {
        return facultyService.getFacultiesWithValueColor(color);
    }

    @GetMapping("/get/students/{id}")
    public Collection<Student> getStudentsWithFaculty(@PathVariable Long id) {
        return facultyService.getFaculty(id).getStudents();
    }

    @GetMapping("/get/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyService.getFaculty(id);
    }

    @PostMapping("/create")
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PostMapping("/create/many")
    public List<Faculty> addFaculties(@RequestBody List<Faculty> faculties) {
        return facultyService.createFaculties(faculties);
    }

    @PutMapping("/update")
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
}
