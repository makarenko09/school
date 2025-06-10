package ru.hogwarts.school.service;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;

@ControllerAdvice(assignableTypes = {StudentController.class, FacultyController.class})
public class MainControllerAdvice extends RuntimeException {
    @ExceptionHandler(NoSuchObjectException.class)
    public ResponseStatus handleNoSuchSomeObjectException(NoSuchObjectException e) {
        return e.getClass().getAnnotation(ResponseStatus.class);
    }
}