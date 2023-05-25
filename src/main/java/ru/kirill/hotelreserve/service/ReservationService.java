package ru.kirill.hotelreserve.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.entity.Hotel;
import ru.kirill.hotelreserve.entity.Reservation;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.exception.EntityNotFoundException;
import ru.kirill.hotelreserve.exception.RoomNotAvailableException;
import ru.kirill.hotelreserve.mapper.ReservationMapper;
import ru.kirill.hotelreserve.repository.HotelRepository;
import ru.kirill.hotelreserve.repository.ReservationRepository;
import ru.kirill.hotelreserve.repository.RoomRepository;

@Service
public class ReservationService extends CRUDService<Reservation,ReservationDto,Long> {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationMapper mapper,
                              RoomRepository roomRepository, HotelRepository hotelRepository) {
        super(reservationRepository, mapper);
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
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
        if (!room.isAvailable()) {
            throw new RoomNotAvailableException("Room " + room.getNumber() + " is not available");
        }
        room.setAvailable(false);
    }

    private Room getRoom(ReservationDto reservationDto) {
        Hotel hotel = findHotelByName(reservationDto.getHotelName());
        return roomRepository
                .findByNumberAndHotel(reservationDto.getRoomNumber(), hotel)
                .orElseThrow(() -> new EntityNotFoundException("Room " + reservationDto.getRoomNumber() + " in " + hotel.getName() + " is not found"));
    }

    private Hotel findHotelByName(String hotelName) {
        return hotelRepository
                .findByName(hotelName)
                .orElseThrow(() -> new EntityNotFoundException("Hotel " + hotelName + " is not found"));
    }
}
