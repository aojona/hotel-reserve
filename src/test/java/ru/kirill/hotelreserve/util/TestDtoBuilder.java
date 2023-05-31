package ru.kirill.hotelreserve.util;

import ru.kirill.hotelreserve.dto.UserRequest;
import ru.kirill.hotelreserve.enums.UserRole;

public class TestDtoBuilder {

    public static UserRequest newCorrectUserRequest() {
        return UserRequest
                .builder()
                .email("test@gmail.com")
                .rawPassword("test")
                .firstName("Dummy")
                .lastName("Dummy")
                .role(UserRole.ADMIN.name())
                .build();
    }

    public static UserRequest newWrongUserRequest() {
        return UserRequest
                .builder()
                .email("not an email")
                .rawPassword("")
                .firstName("")
                .lastName("")
                .role("not a role")
                .build();
    }
}
