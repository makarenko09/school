package ru.hogwarts.school;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.service.NoSuchObjectException;

@ControllerAdvice(assignableTypes = {StudentController.class, FacultyController.class})
public class MainControllerAdvice extends RuntimeException {
    @ExceptionHandler(NoSuchObjectException.class)
    public ResponseStatus handleNoSuchSomeObjectException(NoSuchObjectException e) {
        return e.getClass().getAnnotation(ResponseStatus.class);
    }
}