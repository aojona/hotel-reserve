package ru.kirill.hotelreserve.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.kirill.hotelreserve.dto.UserDto;
import ru.kirill.hotelreserve.entity.User;

import java.util.Optional;

@Component
public class UserMapper extends Mapper<User, UserDto> {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        super(modelMapper, User.class, UserDto.class);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void mapToEntity(UserDto source, User destination) {
        Optional
                .ofNullable(source.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(destination::setPassword);
    }
}
