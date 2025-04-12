package ru.hogwarts.school.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@ControllerAdvice(assignableTypes = {StudentController.class, FacultyService.class})
public class MainControllerAdvice {
    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseEntity<Student> handleNoSuchFieldException(NoSuchFieldException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
