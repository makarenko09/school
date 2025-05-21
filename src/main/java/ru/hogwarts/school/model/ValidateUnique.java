package ru.hogwarts.school.model;

import ru.hogwarts.school.service.FieldValidationException;

public interface ValidateUnique {

    default void validName(String name) {
        if (name.isBlank() || name.matches("\\d+")) {
            throw new FieldValidationException(name);
        }
    }
    String getContentType();
}
