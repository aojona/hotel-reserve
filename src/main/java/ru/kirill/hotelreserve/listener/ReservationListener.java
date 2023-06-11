package ru.kirill.hotelreserve.listener;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import ru.kirill.hotelreserve.dto.HotelReservationDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationListener<E> {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender javaMailSender;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    @EventListener
    public void sendEmailOnReservationEvent(EntityEvent<E> reservationEvent) {
        // написать enum с кастингом и сообщениями
        HotelReservationDto reservation = (HotelReservationDto) reservationEvent.getSource();
        HttpServletRequest request = reservationEvent.getRequest();
        SimpleMailMessage mailMessage = createMessage(reservation, request);
        javaMailSender.send(mailMessage);
        log.info("Sent reservation message to {}", localeResolver.resolveLocale(request));
    }

    private SimpleMailMessage createMessage(HotelReservationDto reservation, HttpServletRequest request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(reservation.getUserEmail());
        mailMessage.setSubject(messageSource.getMessage("reservation.subject", null, localeResolver.resolveLocale(request)));
        mailMessage.setText(messageSource.getMessage("reservation.create", new Object[]{reservation.getHotelName()}, localeResolver.resolveLocale(request)));
        return mailMessage;
    }
}
