package ru.kirill.hotelreserve.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.kirill.hotelreserve.enums.EnumValidator;
import ru.kirill.hotelreserve.enums.UserRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class UserRequest {

    @NotBlank
    private String rawPassword;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @EnumValidator(clazz = UserRole.class, message = "only 'ADMIN' or 'USER' are acceptable")
    String role;
}
