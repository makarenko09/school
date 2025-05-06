package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(short min, short max);

    List<Student> getStudentById(Long id);


//    default Optional<Student> findByIdWithNull(Long aLong) {
//        try {
//        return findById(aLong);
//                    } catch (IllegalArgumentException e) {
//            return Optional.empty();
//        }
//    };
};
