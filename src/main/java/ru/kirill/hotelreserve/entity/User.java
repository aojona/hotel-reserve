package ru.kirill.hotelreserve.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.kirill.hotelreserve.enums.UserRole;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;
}
