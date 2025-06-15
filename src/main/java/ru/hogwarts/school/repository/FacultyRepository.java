package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query(value = "SELECT   name,   LENGTH(name) AS length FROM faculty ORDER BY length DESC LIMIT 1;", nativeQuery = true)
    String getLongestFacultyName();


}

