package ru.kirill.hotelreserve.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.entity.Reservation;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.exception.EntityNotFoundException;
import ru.kirill.hotelreserve.mapper.Mapper;
import ru.kirill.hotelreserve.repository.RoomRepository;
import ru.kirill.hotelreserve.repository.UserRepository;
import java.util.Optional;

@Service
public class ReservationService extends CRUDService<Reservation,ReservationDto,Long>{

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReservationService(JpaRepository<Reservation, Long> jpaRepository,
                              Mapper<Reservation, ReservationDto> mapper,
                              RoomRepository roomRepository,
                              UserRepository userRepository) {
        super(jpaRepository, mapper);
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReservationDto create(ReservationDto reservationDto) {
        return Optional.of(reservationDto)
                .map(mapper::mapToEntity)
                .map(reservation -> {
                    reservation.setRoom(findRoom(reservationDto));
                    reservation.setUser(findUser(reservationDto));
                    return reservation;
                })
                .map(jpaRepository::save)
                .map(mapper::mapToDto)
                .orElseThrow();
    }

    @Override
    public ReservationDto update(Long id, ReservationDto reservationDto) {
        return jpaRepository
                .findById(id)
                .map(reservation -> {
                    reservation.setRoom(findRoom(reservationDto));
                    reservation.setUser(findUser(reservationDto));
                    mapper.updateEntity(reservationDto, reservation);
                    return reservation;
                })
                .map(jpaRepository::saveAndFlush)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    private Room findRoom(ReservationDto reservationDto) {
        return roomRepository
                .findByNumber(reservationDto.getRoomNumber())
                .orElseThrow(() -> new EntityNotFoundException("Room doesn't exist"));
    }

    private User findUser(ReservationDto reservationDto) {
        return userRepository
                .findByEmail(reservationDto.getUserEmail())
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist"));
    }


}
