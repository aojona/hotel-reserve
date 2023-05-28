package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kirill.hotelreserve.dto.HotelReservationDto;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.entity.Reservation;
import ru.kirill.hotelreserve.config.logging.Logging;
import ru.kirill.hotelreserve.service.ReservationService;

import static ru.kirill.hotelreserve.enums.LayerType.CONTROLLER;

@Tag(name = "Reservations")
@Logging(value = CONTROLLER)
@RestController
@RequestMapping("/api/v1/reservations")
@PreAuthorize("permitAll()")
public class ReservationController extends CRUDController<Reservation,ReservationDto,ReservationDto,Long> {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        super(reservationService);
        this.reservationService = reservationService;
    }

    @PostMapping("/reserve")
    @Operation(summary = "Забронить свободный номер")
    public ReservationDto reserveFreeRoom(HotelReservationDto hotelReservationDto) {
        return reservationService.reserveFreeRoom(hotelReservationDto);
    }
}
