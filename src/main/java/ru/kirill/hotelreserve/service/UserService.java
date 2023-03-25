package ru.kirill.hotelreserve.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.UserDto;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.mapper.Mapper;

@Service
public class UserService extends CRUDService<User,UserDto,Long> {

    public UserService(JpaRepository<User, Long> jpaRepository, Mapper<User, UserDto> mapper) {
        super(jpaRepository, mapper);
    }
}
