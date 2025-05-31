package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int min, int max);

    List<Student> findAllByFaculty(Faculty faculty);

    @Query(value = "SELECT COUNT(*) AS count FROM student", nativeQuery = true)
    Integer getCountOfStudentsByName();

    @Query(value = "SELECT AVG(age) AS age FROM student", nativeQuery = true)
    Integer getAverageAgeOfStudentsByAge();

    @Query(value = "SELECT * FROM student ORDER BY id ASC LIMIT 4", nativeQuery = true)
    List<Student> getFiveLateStudentsById();
};
