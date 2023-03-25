package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.kirill.hotelreserve.dto.RoomDto;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.service.CRUDService;

@Tag(name = "Rooms")
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController extends CRUDController<Room,RoomDto,Long> {

    public RoomController(CRUDService<Room, RoomDto, Long> crudService) {
        super(crudService);
    }
}
