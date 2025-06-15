package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository repository;
private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public Collection<Faculty> createFaculties(List<Faculty> faculties) {
        return repository.saveAll(faculties);
    }

    public void putStudentsToFaculty(Long facultyId, List<Student> students) {
        Faculty faculty = repository.findById(facultyId)
                .orElseThrow(() -> new NoSuchObjectException(" - " + facultyId + " does not exist"));

        for (Student student : students) {
            student.setFaculty(faculty);
        }
        studentRepository.saveAll(students);
    }

    public Faculty getFaculty(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchObjectException(" - " + id + " does not exist"));
    }

    public Collection<Faculty> getFacultiesWithValueColor(String color) {
        return repository.findAll().stream()
                .filter(obj -> color.toLowerCase().equals(obj.getColor()))
                .collect(Collectors.toList());
    }

    public Collection<Student> getStudentsByFaculty(Long facultyId) {
        Faculty faculty = repository.findById(facultyId)
                .orElseThrow(() -> new NoSuchObjectException(" - " + facultyId + " does not exist"));
        return studentRepository.findAllByFaculty(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (!repository.existsById(faculty.getId())) {
            throw new NoSuchObjectException(" - " + faculty + " does not exist");
        }
        return repository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        Faculty objDeleted = getFaculty(id);
        repository.delete(objDeleted);
    }
}
