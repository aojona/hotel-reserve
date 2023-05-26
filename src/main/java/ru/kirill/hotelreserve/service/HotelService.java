package ru.kirill.hotelreserve.service;

import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.HotelDto;
import ru.kirill.hotelreserve.entity.Hotel;
import ru.kirill.hotelreserve.mapper.HotelMapper;
import ru.kirill.hotelreserve.repository.HotelRepository;

@Service
public class HotelService extends CRUDService<Hotel,HotelDto,HotelDto,Long> {

    private final HotelRepository hotelRepository;
    public HotelService(HotelRepository hotelRepository, HotelMapper mapper) {
        super(hotelRepository, mapper);
        this.hotelRepository = hotelRepository;
    }

    public Long countAvailableRooms(String name) {
        return hotelRepository.countByNameAndRoomsAvailableTrue(name);
    }
}
