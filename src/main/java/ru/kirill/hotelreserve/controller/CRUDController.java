package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kirill.hotelreserve.dto.ResponseData;
import ru.kirill.hotelreserve.service.CRUDService;
import java.util.List;

@RequiredArgsConstructor
public abstract class CRUDController<E,D,ID extends Number> {

    private final CRUDService<E,D,ID> crudService;

    @GetMapping
    @Operation(summary = "Найти все")
    public ResponseEntity<ResponseData<D>> getRoom() {
        List<D> all = crudService.getAll();
        return new ResponseEntity<>(new ResponseData<>(all), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создать")
    public ResponseEntity<D> createRoom(@RequestBody D roomToCreate) {
        D roomResponse = crudService.create(roomToCreate);
        return new ResponseEntity<>(roomResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти по id")
    public ResponseEntity<D> get(@PathVariable ID id) {
        D roomResponse = crudService.get(id);
        return new ResponseEntity<>(roomResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить по id")
    public ResponseEntity<D> update(@PathVariable ID id, @RequestBody D roomToUpdate) {
        D roomResponse = crudService.update(id, roomToUpdate);
        return new ResponseEntity<>(roomResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить по id")
    public ResponseEntity<HttpStatus> deleteRoom(@PathVariable ID id) {
        crudService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
