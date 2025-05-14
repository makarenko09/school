package ru.hogwarts.school.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
//@ResponseStatus(code = HttpStatus.CONFLICT)
public class NoSuchSomeObjectException extends RuntimeException {
    private String message;

    public NoSuchSomeObjectException(String message) {
        super(message);
    }
}
