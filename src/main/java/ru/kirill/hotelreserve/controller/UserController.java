package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kirill.hotelreserve.dto.UserDto;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.service.CRUDService;

@Tag(name = "Users")
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends CRUDController<User, UserDto, Long> {

    public UserController(CRUDService<User, UserDto, Long> crudService) {
        super(crudService);
    }
}
