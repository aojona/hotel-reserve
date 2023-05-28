package ru.kirill.hotelreserve.service;

import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.RoomDto;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.mapper.RoomMapper;
import ru.kirill.hotelreserve.config.logging.Logging;
import ru.kirill.hotelreserve.repository.RoomRepository;

@Service
@Logging
public class RoomService extends CRUDService<Room,RoomDto,RoomDto,Long> {

    public RoomService(RoomRepository roomRepository, RoomMapper mapper) {
        super(roomRepository, mapper);
    }
}