package ru.hogwarts.school.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchObjectException extends RuntimeException {
    private String message;

    public NoSuchObjectException(String message) {
        super(message);
    }
}
