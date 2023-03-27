package ru.kirill.hotelreserve.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kirill.hotelreserve.dto.RoomDto;
import ru.kirill.hotelreserve.entity.Room;

@Component
public class RoomMapper extends Mapper<Room, RoomDto> {

    public RoomMapper(ModelMapper modelMapper) {
        super(modelMapper, Room.class, RoomDto.class);
    }
}
