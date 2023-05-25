package ru.kirill.hotelreserve.service;


import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.UserDto;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.mapper.UserMapper;
import ru.kirill.hotelreserve.repository.UserRepository;

@Service
public class UserService extends CRUDService<User,UserDto,Long> {

    public UserService(UserRepository userRepository, UserMapper mapper) {
        super(userRepository, mapper);
    }
}
