package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kirill.hotelreserve.dto.HotelReservationDto;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.entity.Reservation;
import ru.kirill.hotelreserve.service.ReservationService;

@Tag(name = "Reservations")
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController extends CRUDController<Reservation,ReservationDto,Long> {

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
