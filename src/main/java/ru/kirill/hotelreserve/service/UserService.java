package ru.kirill.hotelreserve.service;

import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.config.logging.Logging;
import ru.kirill.hotelreserve.dto.UserRequest;
import ru.kirill.hotelreserve.dto.UserResponse;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.mapper.UserMapper;
import ru.kirill.hotelreserve.repository.UserRepository;

@Service
@Logging
public class UserService extends CRUDService<User,UserRequest,UserResponse,Long>  {

    public UserService(UserRepository userRepository, UserMapper mapper) {
        super(userRepository, mapper);

    }
}
