package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kirill.hotelreserve.dto.HotelDto;
import ru.kirill.hotelreserve.entity.Hotel;
import ru.kirill.hotelreserve.service.CRUDService;

@Tag(name = "Hotels")
@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController extends CRUDController<Hotel, HotelDto,Long> {

    public HotelController(CRUDService<Hotel, HotelDto,Long> crudService) {
        super(crudService);
    }
}
