package ru.kirill.hotelreserve.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.HotelReservationDto;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.entity.Reservation;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.exception.EntityNotFoundException;
import ru.kirill.hotelreserve.exception.RoomNotAvailableException;
import ru.kirill.hotelreserve.mapper.ReservationMapper;
import ru.kirill.hotelreserve.repository.ReservationRepository;
import ru.kirill.hotelreserve.repository.RoomRepository;

@Service
public class ReservationService extends CRUDService<Reservation,ReservationDto,ReservationDto,Long> {

    private final RoomRepository roomRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationMapper mapper,
                              RoomRepository roomRepository) {
        super(reservationRepository, mapper);
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

    @Transactional
    public ReservationDto reserveFreeRoom(HotelReservationDto hotelReservationDto) {

        Room room = findFreeRoomByHotelName(hotelReservationDto.getHotelName());
        reserveRoom(room);
        return super.create(createReservationDto(hotelReservationDto, room));
    }

    private void vacateRoom(ReservationDto reservationDto) {
        Room room = findRoomByNumberAndHotelName(reservationDto.getRoomNumber(), reservationDto.getHotelName());
        room.setAvailable(true);
        roomRepository.saveAndFlush(room);
    }

    private void reserveRoom(ReservationDto reservationDto) {
        Room room = findRoomByNumberAndHotelName(reservationDto.getRoomNumber(), reservationDto.getHotelName());
        reserveRoom(room);
    }

    private static void reserveRoom(Room room) {
        if (!room.isAvailable()) {
            throw new RoomNotAvailableException("Room " + room.getNumber() + " is not available");
        }
        room.setAvailable(false);
    }

    private Room findRoomByNumberAndHotelName(Integer roomNumber, String hotelName) {
        return roomRepository
                .findByNumberAndHotelName(roomNumber, hotelName)
                .orElseThrow(() -> new EntityNotFoundException("Room " + roomNumber + " in " + hotelName + " is not found"));
    }

    private Room findFreeRoomByHotelName(String hotelName) {
        return roomRepository
                .findTop1ByHotelNameAndAvailableTrue(hotelName)
                .orElseThrow(() -> new RoomNotAvailableException("There are no available rooms in hotel: " + hotelName));
    }

    private static ReservationDto createReservationDto(HotelReservationDto reservationDto, Room room) {
        return ReservationDto
                .builder()
                .roomNumber(room.getNumber())
                .hotelName(reservationDto.getHotelName())
                .userEmail(reservationDto.getUserEmail())
                .build();
    }
}
