package ru.kirill.hotelreserve.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.RoomDto;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.mapper.Mapper;

@Service
public class RoomService extends CRUDService<Room,RoomDto,Long> {

    public RoomService(JpaRepository<Room, Long> jpaRepository, Mapper<Room, RoomDto> mapper) {
        super(jpaRepository, mapper);
    }
}