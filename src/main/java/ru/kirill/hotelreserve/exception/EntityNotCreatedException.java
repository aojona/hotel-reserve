package ru.kirill.hotelreserve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotCreatedException extends ResponseStatusException {
    public EntityNotCreatedException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
