package ru.kirill.hotelreserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kirill.hotelreserve.entity.Hotel;
import ru.kirill.hotelreserve.entity.Room;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    Optional<Room> findByNumberAndHotel(Integer number, Hotel hotel);
}
