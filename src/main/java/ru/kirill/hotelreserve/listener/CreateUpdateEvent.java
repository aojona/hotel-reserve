package ru.kirill.hotelreserve.listener;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.util.EventObject;

@Getter
public class CreateUpdateEvent<E> extends EventObject {

    private final HttpServletRequest request;
    private final String eventType;

    public CreateUpdateEvent(E source, HttpServletRequest request, String eventType) {
        super(source);
        this.request = request;
        this.eventType = eventType;
    }
}
