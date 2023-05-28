package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kirill.hotelreserve.dto.UserRequest;
import ru.kirill.hotelreserve.dto.UserResponse;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.config.logging.Logging;
import ru.kirill.hotelreserve.service.UserService;

import static ru.kirill.hotelreserve.enums.LayerType.CONTROLLER;

@Tag(name = "Users")
@Logging(value = CONTROLLER)
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends CRUDController<User,UserRequest,UserResponse,Long> {

    public UserController(UserService userService) {
        super(userService);
    }
}
