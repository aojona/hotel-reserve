package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.kirill.hotelreserve.dto.RoomDto;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.config.logging.Logging;
import ru.kirill.hotelreserve.service.RoomService;

import static ru.kirill.hotelreserve.enums.LayerType.CONTROLLER;

@Tag(name = "Rooms")
@Logging(value = CONTROLLER)
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController extends CRUDController<Room,RoomDto,RoomDto,Long> {

    public RoomController(RoomService roomService) {
        super(roomService);
    }
}
