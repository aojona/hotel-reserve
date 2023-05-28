package ru.kirill.hotelreserve.config.logging;

import ru.kirill.hotelreserve.enums.LayerType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.kirill.hotelreserve.enums.LayerType.DEFAULT_LAYER;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging {

    LayerType value() default DEFAULT_LAYER;
}