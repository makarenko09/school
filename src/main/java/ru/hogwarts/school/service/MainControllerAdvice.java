package ru.hogwarts.school.service;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@ControllerAdvice(assignableTypes = {StudentController.class, FacultyController.class})
public class MainControllerAdvice extends RuntimeException {
    @ExceptionHandler(NoSuchSomeObjectException.class)
    public ResponseStatus handleNoSuchSomeObjectException(NoSuchSomeObjectException e) {
        return e.getClass().getAnnotation(ResponseStatus.class);
    }
    @ExceptionHandler(FieldValidationException.class)
    public ResponseStatus handleFieldValidationException(FieldValidationException e) {
        return e.getClass().getAnnotation(ResponseStatus.class);
    }
}