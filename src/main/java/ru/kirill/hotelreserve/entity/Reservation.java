package ru.kirill.hotelreserve.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
// переделать на check_in_date
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "room_id"})})
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "room_id")
    private Room room;
}