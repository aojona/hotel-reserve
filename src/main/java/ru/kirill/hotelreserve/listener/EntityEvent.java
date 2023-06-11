package ru.kirill.hotelreserve.listener;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.util.EventObject;

@Getter
public class EntityEvent<E> extends EventObject {

    private final HttpServletRequest request;

    public EntityEvent(E source, HttpServletRequest request) {
        super(source);
        this.request = request;
    }
}
