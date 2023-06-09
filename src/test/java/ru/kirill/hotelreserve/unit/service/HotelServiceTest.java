package ru.kirill.hotelreserve.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kirill.hotelreserve.repository.HotelRepository;
import ru.kirill.hotelreserve.service.HotelService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @Test
    void shouldCountAvailableRooms() {
        String roomName = "Double";
        long roomNumber = 10;
        doReturn(roomNumber)
                .when(hotelRepository)
                .countByNameAndRoomsAvailableTrue(roomName);

        long actualResult = hotelService.countAvailableRooms(roomName);

        assertThat(actualResult).isEqualTo(roomNumber);
    }
}
