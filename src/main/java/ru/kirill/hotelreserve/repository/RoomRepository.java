package ru.kirill.hotelreserve.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kirill.hotelreserve.entity.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = "hotel")
    List<Room> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = "hotel")
    Page<Room> findAll(@NonNull Pageable pageable);

    Optional<Room> findByNumberAndHotelName(Integer number, String hotelName);

    Optional<Room> findTop1ByHotelNameAndAvailableTrue(String hotelName);
}
