package ru.kirill.hotelreserve.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kirill.hotelreserve.dto.UserDto;
import ru.kirill.hotelreserve.entity.User;

@Component
public class UserMapper extends Mapper<User, UserDto> {

    public UserMapper(ModelMapper modelMapper) {
        super(modelMapper, User.class, UserDto.class);
    }
}
