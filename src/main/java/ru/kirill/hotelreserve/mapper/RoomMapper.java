package ru.kirill.hotelreserve.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kirill.hotelreserve.dto.RoomDto;
import ru.kirill.hotelreserve.entity.Hotel;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.exception.EntityNotFoundException;
import ru.kirill.hotelreserve.repository.HotelRepository;

@Component
public class RoomMapper extends Mapper<Room, RoomDto> {

    private final HotelRepository hotelRepository;

    public RoomMapper(ModelMapper modelMapper, HotelRepository hotelRepository) {
        super(modelMapper, Room.class, RoomDto.class);
        this.hotelRepository = hotelRepository;
    }

    @Override
    protected void mapToDto(Room source, RoomDto destination) {
        destination.setHotelName(source.getHotel().getName());
    }

    @Override
    protected void mapToEntity(RoomDto source, Room destination) {
        Hotel hotel = findHotelByName(source.getHotelName());
        destination.setHotel(hotel);
    }

    private Hotel findHotelByName(String hotelName) {
        return hotelRepository
                .findByName(hotelName)
                .orElseThrow(() -> new EntityNotFoundException("Hotel " + hotelName + " is not found"));
    }
}
