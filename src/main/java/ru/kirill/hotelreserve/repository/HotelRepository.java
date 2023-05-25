package ru.kirill.hotelreserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kirill.hotelreserve.entity.Hotel;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByName(String name);

    @Query(value = """
                SELECT COUNT(*)
                FROM Hotel h
                JOIN h.rooms r
                WHERE h.name = :name AND r.available = true"""
    )
    long countAvailableRooms(String name);
}
