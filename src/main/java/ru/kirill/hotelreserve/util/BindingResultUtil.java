package ru.kirill.hotelreserve.util;

import org.springframework.validation.BindingResult;

public class BindingResultUtil {

    public static String getErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        bindingResult
                .getFieldErrors()
                .forEach(er -> errorMessage
                    .append(er.getField())
                    .append(": ")
                    .append(er.getDefaultMessage())
                    .append("; ")
        );
        return errorMessage.toString();
    }
}