package ru.kirill.hotelreserve.unit.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.repository.UserRepository;
import ru.kirill.hotelreserve.service.UserDetailsServiceImpl;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static ru.kirill.hotelreserve.util.TestObjectBuilder.getUser;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    private static final String EMAIL = "dummy@gmail.com";

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldLoadUserByCorrectUsername() {
        User expectedResult = getUser();
        doReturn(Optional.of(expectedResult))
                .when(userRepository)
                .findByEmail(EMAIL);
        UserDetails actualResult = userDetailsService.loadUserByUsername(EMAIL);
        assertAll(
                () -> assertThat(actualResult.getUsername())
                        .isEqualTo(expectedResult.getEmail()),
                () -> assertThat(actualResult.getPassword())
                        .isEqualTo(expectedResult.getPassword()),
                () -> assertThat(actualResult.getAuthorities())
                        .isEqualTo(Collections.singleton(expectedResult.getRole()))
        );
    }

    @Test
    void shouldThrowExceptionWhenLoadUserByWrongUsername() {
        doReturn(Optional.empty())
                .when(userRepository)
                .findByEmail(EMAIL);
        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(EMAIL)
        );
    }
}