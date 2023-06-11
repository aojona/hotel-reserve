package ru.kirill.hotelreserve.enums;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumValidator {
    Class<? extends Enum<?>> clazz();
    String message() default "must be any of enum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}