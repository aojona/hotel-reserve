package ru.kirill.hotelreserve.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
}
