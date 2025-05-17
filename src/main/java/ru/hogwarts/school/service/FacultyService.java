package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
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

    public List<Faculty> createFaculties(List<Faculty> faculties) {
        return repository.saveAll(faculties);
    }

    public Faculty getFaculty(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchSomeObjectException(" - " + id + " does not exist"));
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (!repository.existsById(faculty.getId())) {
            throw new NoSuchSomeObjectException(" - " + faculty + " does not exist");
        }
            return repository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        Faculty objDeleted = getFaculty(id);
        repository.delete(objDeleted);
    }

    public Collection<Faculty> getFacultiesWithValueColor(String color) {
        return repository.findAll().stream()
                .filter(obj -> color.toLowerCase().equals(obj.getColor()))
                .collect(Collectors.toList());
    }
}
