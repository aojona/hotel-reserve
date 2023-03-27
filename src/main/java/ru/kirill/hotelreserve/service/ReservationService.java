package ru.kirill.hotelreserve.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kirill.hotelreserve.dto.ReservationDto;
import ru.kirill.hotelreserve.entity.Reservation;

import ru.kirill.hotelreserve.mapper.Mapper;

@Service
public class ReservationService extends CRUDService<Reservation,ReservationDto,Long> {

    public ReservationService(JpaRepository<Reservation, Long> jpaRepository,
                              Mapper<Reservation, ReservationDto> mapper) {
        super(jpaRepository, mapper);
    }
}
