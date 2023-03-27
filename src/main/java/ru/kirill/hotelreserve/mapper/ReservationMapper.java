package ru.kirill.hotelreserve.mapper;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void setup() {
        modelMapper
                .createTypeMap(Reservation.class, ReservationDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper
                .createTypeMap(ReservationDto.class, Reservation.class)
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapToDto(Reservation source, ReservationDto destination) {
        destination.setRoomNumber(source.getRoom().getNumber());
        destination.setUserEmail(source.getUser().getEmail());
    }

    @Override
    protected void mapToEntity(ReservationDto source, Reservation destination) {
        destination.setRoom(findRoom(source));
        destination.setUser(findUser(source));
    }

    private Room findRoom(ReservationDto source) {
        Integer roomNumber = source.getRoomNumber();
        return roomRepository
                .findByNumber(roomNumber)
                .orElseThrow(() -> new EntityNotFoundException("Room number " + roomNumber + " doesn't exist"));
    }

    private User findUser(ReservationDto source) {
        String userEmail = source.getUserEmail();
        return userRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + userEmail + " doesn't exist"));
    }
}
