package ru.kirill.hotelreserve.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.List;

public class BindingResultUtil {

    public static String getErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        errors.forEach(er -> errorMessage
                    .append(er.getField())
                    .append(": ")
                    .append(er.getDefaultMessage())
                    .append("; ")
        );
        return errorMessage.toString();
    }
}