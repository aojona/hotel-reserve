package ru.kirill.hotelreserve.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReservationDto extends HotelReservationDto {

    @NotNull
    private Integer roomNumber;
}
