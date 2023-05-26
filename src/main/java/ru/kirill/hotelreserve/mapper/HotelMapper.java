package ru.kirill.hotelreserve.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kirill.hotelreserve.dto.HotelDto;
import ru.kirill.hotelreserve.entity.Hotel;

@Component
public class HotelMapper extends Mapper<Hotel, HotelDto, HotelDto> {

    public HotelMapper(ModelMapper modelMapper) {
        super(modelMapper, Hotel.class, HotelDto.class, HotelDto.class);
    }
}
