package ru.kirill.hotelreserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kirill.hotelreserve.entity.Hotel;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByName(String name);

    long countByNameAndRoomsAvailableTrue(String name);
}
