package ru.kirill.hotelreserve.enums;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.LocaleResolver;
import ru.kirill.hotelreserve.dto.HotelDto;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.dto.RoomDto;
import ru.kirill.hotelreserve.dto.UserResponse;
import ru.kirill.hotelreserve.listener.CreateUpdateEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
public enum SourceType {

    RESERVATION(ReservationDto.class) {
        @Override
        public void setMessage(CreateUpdateEvent<?> event, MessageSource messageSource,
                               LocaleResolver localeResolver, SimpleMailMessage message) {
            ReservationDto source = (ReservationDto) event.getSource();
            String emailDestination = source.getUserEmail();
            message.setTo(source.getUserEmail());
            Locale locale = localeResolver.resolveLocale(event.getRequest());
            Object[] messageArguments = new Object[]{source.getHotelName(), source.getRoomNumber()};
            setMessageText(event, messageSource, message, messageArguments, locale, "reservation");
            logReservation(emailDestination);
        }
    },
    USER(UserResponse.class) {
        @Override
        public void setMessage(CreateUpdateEvent<?> event, MessageSource messageSource,
                               LocaleResolver localeResolver, SimpleMailMessage message) {
            UserResponse source = (UserResponse) event.getSource();
            String emailDestination = source.getEmail();
            message.setTo(emailDestination);
            Locale locale = localeResolver.resolveLocale(event.getRequest());
            Object[] messageArguments = new Object[]{source.getFirstName(), source.getLastName()};
            setMessageText(event, messageSource, message, messageArguments, locale, "user");
            logReservation(emailDestination);
        }
    },
    HOTEL(HotelDto.class) {
        @Override
        public void setMessage(CreateUpdateEvent<?> event, MessageSource messageSource,
                               LocaleResolver localeResolver, SimpleMailMessage message) {
            HotelDto source = (HotelDto) event.getSource();
            Locale locale = localeResolver.resolveLocale(event.getRequest());
            setMessageText(event, messageSource, message, new Object[]{source.getName()}, locale, "hotel");
        }
    },
    ROOM(RoomDto.class) {
        @Override
        public void setMessage(CreateUpdateEvent<?> event, MessageSource messageSource,
                               LocaleResolver localeResolver, SimpleMailMessage message) {
            RoomDto source = (RoomDto) event.getSource();
            Locale locale = localeResolver.resolveLocale(event.getRequest());
            Object[] messageArguments = new Object[]{source.getHotelName(), source.getNumber()};
            setMessageText(event, messageSource, message, messageArguments, locale, "room");
        }
    };

    private final Class<?> clazz;

    private static final Map<Class<?>, SourceType> map = new HashMap<>();

    static {
        Arrays
                .stream(values())
                .sequential()
                .forEach(event -> map.put(event.clazz, event));
    }

    SourceType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public abstract void setMessage(CreateUpdateEvent<?> event, MessageSource messageSource,
                                    LocaleResolver localeResolver, SimpleMailMessage message);

    public static SourceType get(Class<?> clazz) {
        SourceType sourceType = map.get(clazz);
        if (sourceType == null) {
            throw new IllegalArgumentException("Event type for " + clazz.getSimpleName() + " is not found");
        }
        return sourceType;
    }

    private static void logReservation(String email) {
        log.info("Sent reservation message to {}", email);
    }

    private static void setMessageText(CreateUpdateEvent<?> event, MessageSource messageSource,
                                       SimpleMailMessage message, Object[] messageArguments, Locale locale, String type) {
        message.setSubject(messageSource.getMessage(type + ".subject", null, locale));
        message.setText(
                messageSource.getMessage(type + "." + event.getEventType(), messageArguments, locale) +
                messageSource.getMessage("regards", null, locale)
        );
    }
}