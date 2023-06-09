package ru.kirill.hotelreserve.util;

import ru.kirill.hotelreserve.dto.UserRequest;
import ru.kirill.hotelreserve.dto.UserResponse;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.enums.UserRole;
import java.util.List;
import java.util.stream.IntStream;

public class TestObjectBuilder {

    private static final String EMAIL = "dummy@gmail.com";
    private static final String PASSWORD = "dummy";
    private static final String FIRST_NAME = "Jonathan";
    private static final String LAST_NAME = "Swift";
    private static final String EMAIL_2 = "test@gmail.com";
    private static final String PASSWORD_2 = "test";
    private static final String FIRST_NAME_2 = "Nikola";
    private static final String LAST_NAME_2 = "Tesla";
    private static final int LIST_SIZE = 5;

    public static UserRequest getCorrectUserRequest() {
        return UserRequest
                .builder()
                .email(EMAIL)
                .rawPassword(PASSWORD)
                .firstName(FIRST_NAME)
                .lastName(FIRST_NAME )
                .role(UserRole.ADMIN.name())
                .build();
    }

    public static UserRequest getUpdatedRequest() {
        return UserRequest
                .builder()
                .rawPassword(PASSWORD_2)
                .email(EMAIL_2)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .build();
    }

    public static User getUser() {
        return User
                .builder()
                .email(EMAIL)
                .password(PASSWORD)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME )
                .role(UserRole.ADMIN)
                .build();
    }

    public static User getUpdatedUser() {
        return User
                .builder()
                .password(PASSWORD_2)
                .email(EMAIL_2)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .build();
    }

    public static UserResponse getUserResponse() {
        return UserResponse
                .builder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME )
                .role(UserRole.ADMIN.name())
                .build();
    }

    public static List<UserResponse> getUserListResponse() {
        return IntStream
                .range(0, LIST_SIZE)
                .mapToObj(i -> getUserResponse())
                .toList();
    }

    public static List<User> getUserList() {
        return IntStream
                .range(0, LIST_SIZE)
                .mapToObj(i -> getUser())
                .toList();
    }

    public static UserRequest getWrongUserRequest() {
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
