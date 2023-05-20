package ru.kirill.hotelreserve.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kirill.hotelreserve.exception.ExceptionResponse;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {

    private final HttpStatus httpStatusBR = HttpStatus.BAD_REQUEST;

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(SQLException e, HttpServletRequest httpServletRequest) {
        ExceptionResponse response = new ExceptionResponse(
                LocalDateTime.now(),
                httpStatusBR.value(),
                e.getMessage(),
                httpServletRequest.getRequestURL().toString());
        return new ResponseEntity<>(response, httpStatusBR);
    }
}
