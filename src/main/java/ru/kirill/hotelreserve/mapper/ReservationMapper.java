package ru.kirill.hotelreserve.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.entity.Hotel;
import ru.kirill.hotelreserve.entity.Reservation;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.exception.EntityNotFoundException;
import ru.kirill.hotelreserve.repository.HotelRepository;
import ru.kirill.hotelreserve.repository.RoomRepository;
import ru.kirill.hotelreserve.repository.UserRepository;

@Component
public class ReservationMapper extends Mapper<Reservation, ReservationDto> {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public ReservationMapper(ModelMapper modelMapper, UserRepository userRepository, RoomRepository roomRepository,
                             HotelRepository hotelRepository) {
        super(modelMapper, Reservation.class, ReservationDto.class);
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    protected void mapToDto(Reservation source, ReservationDto destination) {
        destination.setRoomNumber(source.getRoom().getNumber());
        destination.setUserEmail(source.getUser().getEmail());
        destination.setHotelName(source.getHotel().getName());
    }

    @Override
    protected void mapToEntity(ReservationDto source, Reservation destination) {
        Hotel hotel = findHotel(source.getHotelName());
        destination.setRoom(findRoom(source.getRoomNumber(), hotel));
        destination.setUser(findUser(source.getUserEmail()));
        destination.setHotel(hotel);
    }

    private Room findRoom(Integer roomNumber, Hotel hotel) {
        return roomRepository
                .findByNumberAndHotel(roomNumber, hotel)
                .orElseThrow(() -> new EntityNotFoundException("Room " + roomNumber + " in hotel " + hotel.getName() + " is not found"));
    }

    private User findUser(String userEmail) {
        return userRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User email " + userEmail + " is not found"));
    }

    private Hotel findHotel(String hotelName) {
        return hotelRepository
                .findByName(hotelName)
                .orElseThrow(() -> new EntityNotFoundException("Hotel " + hotelName + " is not found"));
    }
}
