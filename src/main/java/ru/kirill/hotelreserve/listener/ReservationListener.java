package ru.kirill.hotelreserve.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import ru.kirill.hotelreserve.enums.SourceType;

@Component
@RequiredArgsConstructor
public class ReservationListener<E> {

    @Value("${spring.mail.username}")
    private String addressee;

    private final JavaMailSender javaMailSender;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    @EventListener
    public void sendEmailOnReservationEvent(CreateUpdateEvent<E> event) {
        SourceType sourceType = SourceType.get(event.getSource().getClass());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(addressee);
        sourceType.setMessage(
                event,
                messageSource,
                localeResolver,
                mailMessage
        );
        if (mailMessage.getTo() == null) {
            mailMessage.setTo(addressee);
        }
        javaMailSender.send(mailMessage);
    }
}
