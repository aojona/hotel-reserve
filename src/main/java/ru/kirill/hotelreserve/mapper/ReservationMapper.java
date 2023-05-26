package ru.kirill.hotelreserve.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.entity.Reservation;
import ru.kirill.hotelreserve.entity.Room;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.exception.EntityNotFoundException;
import ru.kirill.hotelreserve.repository.RoomRepository;
import ru.kirill.hotelreserve.repository.UserRepository;

@Component
public class ReservationMapper extends Mapper<Reservation, ReservationDto> {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationMapper(ModelMapper modelMapper, UserRepository userRepository, RoomRepository roomRepository) {
        super(modelMapper, Reservation.class, ReservationDto.class);
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    protected void mapToDto(Reservation source, ReservationDto destination) {
        destination.setRoomNumber(source.getRoom().getNumber());
        destination.setUserEmail(source.getUser().getEmail());
        destination.setHotelName(source.getHotel().getName());
    }

    @Override
    protected void mapToEntity(ReservationDto source, Reservation destination) {
        Room room = findRoom(source.getRoomNumber(), source.getHotelName());
        destination.setRoom(room);
        destination.setUser(findUser(source.getUserEmail()));
        destination.setHotel(room.getHotel());
    }

    private Room findRoom(Integer roomNumber, String hotelName) {
        return roomRepository
                .findByNumberAndHotelName(roomNumber, hotelName)
                .orElseThrow(() -> new EntityNotFoundException("Room " + roomNumber + " in hotel " + hotelName + " is not found"));
    }

    private User findUser(String userEmail) {
        return userRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User email " + userEmail + " is not found"));
    }
}
