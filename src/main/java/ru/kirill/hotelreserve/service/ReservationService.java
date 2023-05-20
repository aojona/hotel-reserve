package ru.kirill.hotelreserve.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.entity.Reservation;

import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.exception.EntityNotFoundException;
import ru.kirill.hotelreserve.exception.RoomNotAvailableException;
import ru.kirill.hotelreserve.mapper.Mapper;
import ru.kirill.hotelreserve.repository.RoomRepository;

@Service
public class ReservationService extends CRUDService<Reservation,ReservationDto,Long> {

    private final RoomRepository roomRepository;

    @Autowired
    public ReservationService(JpaRepository<Reservation, Long> jpaRepository, Mapper<Reservation, ReservationDto> mapper,
                              RoomRepository roomRepository) {
        super(jpaRepository, mapper);
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public ReservationDto create(ReservationDto reservationDto) {
        reserveRoom(reservationDto);
        return super.create(reservationDto);
    }

    @Override
    @Transactional
    public ReservationDto update(Long id, ReservationDto reservationDto) {
        reserveRoom(reservationDto);
        vacateRoom(super.get(id));
        return super.update(id, reservationDto);
    }

    private void vacateRoom(ReservationDto reservationDto) {
        Room room = getRoom(reservationDto);
        room.setAvailable(true);
        roomRepository.saveAndFlush(room);
    }

    private void reserveRoom(ReservationDto reservationDto) {
        Room room = getRoom(reservationDto);
        throwExceptionIfRoomIsNotAvailable(room);
        room.setAvailable(false);
    }

    private Room getRoom(ReservationDto reservationDto) {
        return roomRepository
                .findByNumber(reservationDto.getRoomNumber())
                .orElseThrow(() -> new EntityNotFoundException("Room " + reservationDto.getRoomNumber() + " doesn't exist"));
    }
    private void throwExceptionIfRoomIsNotAvailable(Room room) {
        if (!room.isAvailable()) {
            throw new RoomNotAvailableException("Room " + room.getNumber() + " is not available");
        }
    }
}
