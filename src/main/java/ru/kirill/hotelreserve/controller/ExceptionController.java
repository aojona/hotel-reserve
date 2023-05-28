package ru.kirill.hotelreserve.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kirill.hotelreserve.exception.ExceptionResponse;
import java.sql.SQLException;
import java.time.LocalDateTime;


@Slf4j
@RestControllerAdvice
public class ExceptionController {

    private ResponseEntity<ExceptionResponse> handleException(Exception e, HttpServletRequest httpServletRequest,
                                                              HttpStatus httpStatus) {
        log.info("{}: {}, message: {}", httpServletRequest.getRequestURI(), httpStatus, e.getMessage());
        ExceptionResponse response = new ExceptionResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                e.getMessage(),
                httpServletRequest.getRequestURL().toString());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(SQLException e, HttpServletRequest httpServletRequest) {
        return handleException(e, httpServletRequest, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException e, HttpServletRequest httpServletRequest) {
        return handleException(e, httpServletRequest, HttpStatus.NOT_FOUND);
    }

}
