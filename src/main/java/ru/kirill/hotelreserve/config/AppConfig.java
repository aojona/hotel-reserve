package ru.kirill.hotelreserve.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.dto.RoomDto;
import ru.kirill.hotelreserve.dto.UserDto;
import ru.kirill.hotelreserve.entity.Reservation;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.mapper.Mapper;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @DependsOn("modelMapper")
    public Mapper<Reservation, ReservationDto> reservationMapper(ModelMapper modelMapper) {
        return new Mapper<>(modelMapper, Reservation.class, ReservationDto.class);
    }

    @Bean
    @DependsOn("modelMapper")
    public Mapper<Room,RoomDto> roomMapper(ModelMapper modelMapper) {
        return new Mapper<>(modelMapper, Room.class, RoomDto.class);
    }

    @Bean
    @DependsOn("modelMapper")
    public Mapper<User, UserDto> userMapper(ModelMapper modelMapper) {
        return new Mapper<>(modelMapper, User.class, UserDto.class);
    }
}
