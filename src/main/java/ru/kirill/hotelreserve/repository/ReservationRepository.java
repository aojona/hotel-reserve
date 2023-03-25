package ru.kirill.hotelreserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kirill.hotelreserve.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
