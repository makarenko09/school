package ru.hogwarts.school.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@ControllerAdvice(assignableTypes = {StudentController.class, FacultyController.class})
public class MainControllerAdvice extends RuntimeException {
    @ExceptionHandler(NoSuchSomeObjectException.class)
    public ResponseEntity<Object> handleNoSuchSomeObjectException(NoSuchSomeObjectException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}