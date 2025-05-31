package ru.hogwarts.school.service;

import jakarta.persistence.EntityNotFoundException;
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

    public void addStudentsToFaculty(Long facultyId, List<Student> students) {
        // Получаем факультет по ID (или выбрасываем исключение, если не найден)
        Faculty faculty = repository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found by id: " + facultyId));

        // Устанавливаем связь "каждый студент -> факультет"
        for (Student student : students) {
            student.setFaculty(faculty);
        }
        // Сохраняем всех студентов в БД, вместе с обновлённым foreign key
        studentRepository.saveAll(students);
    }
    public List<Student> getStudentsByFaculty(Long facultyId) {
        // Достаём факультет для проверки наличия
        Faculty faculty = repository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found by id: " + facultyId));

        // Возвращаем всех студентов с данным факультетом
        return studentRepository.findAllByFaculty(faculty);
    }

    public Faculty createFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public List<Faculty> createFaculties(List<Faculty> faculties) {
        return repository.saveAll(faculties);
    }

    public Faculty getFaculty(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchObjectException(" - " + id + " does not exist"));
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

    public Collection<Faculty> getFacultiesWithValueColor(String color) {
        return repository.findAll().stream()
                .filter(obj -> color.toLowerCase().equals(obj.getColor()))
                .collect(Collectors.toList());
    }
}
