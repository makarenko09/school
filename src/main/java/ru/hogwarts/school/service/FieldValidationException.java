package ru.hogwarts.school.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FieldValidationException extends RuntimeException {
    private String message;

    public FieldValidationException(String message) {
        super("- (" + message + ") value is invalid.");
    }
}
