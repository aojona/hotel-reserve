package ru.kirill.hotelreserve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotUpdatedException extends ResponseStatusException {
    public EntityNotUpdatedException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
