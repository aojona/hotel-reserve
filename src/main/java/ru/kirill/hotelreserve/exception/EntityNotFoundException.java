package ru.kirill.hotelreserve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {

    public EntityNotFoundException(Number id) {
        super(HttpStatus.NOT_FOUND, "entity with id " + id + " is not found");
    }

    public EntityNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}