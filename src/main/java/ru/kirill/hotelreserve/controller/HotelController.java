package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kirill.hotelreserve.dto.HotelDto;
import ru.kirill.hotelreserve.entity.Hotel;
import ru.kirill.hotelreserve.config.logging.Logging;
import ru.kirill.hotelreserve.service.HotelService;

import static ru.kirill.hotelreserve.enums.LayerType.CONTROLLER;

@Tag(name = "Hotels")
@Logging(value = CONTROLLER)
@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController extends CRUDController<Hotel,HotelDto,HotelDto,Long> {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService, ApplicationEventPublisher eventPublisher) {
        super(hotelService, eventPublisher);
        this.hotelService = hotelService;
    }

    @GetMapping("/available")
    @Operation(summary = "Узнать количество свободных номеров")
    public ResponseEntity<Long> countAvailableRooms(@RequestParam String name) {
        return new ResponseEntity<>(hotelService.countAvailableRooms(name), HttpStatus.OK);
    }
}
