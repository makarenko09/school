package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new NoSuchSomeObjectException(" - " + id + " does not exist");
        }
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (repository.findById(faculty.getId()).isPresent()) {
            return repository.save(faculty);
        } else {
            throw new NoSuchSomeObjectException(" - " + faculty + " does not exist");
        }
    }

    public void deleteFaculty(long id) {
        Faculty objDeleted = getFaculty(id);
        repository.delete(objDeleted);
    }

    public Collection<Faculty> getFacultiesWithValueColor(String color) {
        return repository.findAll().stream()
                .filter(obj -> color.toLowerCase().equals(obj.getColor()))
                .collect(Collectors.toList());
    }
}
