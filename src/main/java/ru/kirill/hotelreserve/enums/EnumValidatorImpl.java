package ru.kirill.hotelreserve.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    private List<String> acceptedValues;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return acceptedValues.contains(value);
    }

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        acceptedValues = Arrays.stream(constraintAnnotation
                .clazz()
                .getEnumConstants())
                .map(Enum::name)
                .toList();
    }
}