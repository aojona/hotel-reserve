package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kirill.hotelreserve.dto.HotelReservationDto;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.entity.Reservation;
import ru.kirill.hotelreserve.config.logging.Logging;
import ru.kirill.hotelreserve.listener.CreateUpdateEvent;
import ru.kirill.hotelreserve.service.ReservationService;

import static ru.kirill.hotelreserve.enums.LayerType.CONTROLLER;

@Tag(name = "Reservations")
@Logging(value = CONTROLLER)
@RestController
@RequestMapping("/api/v1/reservations")
@PreAuthorize("permitAll()")
public class ReservationController extends CRUDController<Reservation,ReservationDto,ReservationDto,Long> {

    private final ReservationService reservationService;
    private final ApplicationEventPublisher eventPublisher;

    public ReservationController(ReservationService reservationService, ApplicationEventPublisher eventPublisher) {
        super(reservationService, eventPublisher);
        this.reservationService = reservationService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/reserve")
    @Operation(summary = "Забронить любой свободный номер")
    @Parameter(name = "lang", allowEmptyValue = true, description = "en(default)/ru")
    public ReservationDto reserveFreeRoom(HotelReservationDto hotelReservationDto, HttpServletRequest request) {
        ReservationDto reservationDto = reservationService.reserveFreeRoom(hotelReservationDto);
        eventPublisher.publishEvent(new CreateUpdateEvent<>(hotelReservationDto, request, "create"));
        return reservationDto;
    }
}
