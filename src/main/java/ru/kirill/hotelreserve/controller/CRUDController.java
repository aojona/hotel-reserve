package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kirill.hotelreserve.dto.ResponseData;
import ru.kirill.hotelreserve.exception.EntityNotCreatedException;
import ru.kirill.hotelreserve.exception.EntityNotUpdatedException;
import ru.kirill.hotelreserve.service.CRUDService;
import ru.kirill.hotelreserve.util.BindingResultUtil;

import java.util.List;

@RequiredArgsConstructor
public abstract class CRUDController<E,D1,D2,ID extends Number> {

    private final CRUDService<E,D1,D2,ID> crudService;

    @GetMapping
    @Operation(summary = "Найти все")
    public ResponseEntity<ResponseData<D2>> getAll(@RequestParam(required = false) int page,
                                                  @RequestParam(required = false) int size) {
        List<D2> all = (page < 0 || size <= 0)
                ? crudService.getAll()
                : crudService.getPage(page, size);
        return new ResponseEntity<>(new ResponseData<>(all), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создать")
    public ResponseEntity<D2> create(@RequestBody @Valid D1 requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = BindingResultUtil.getErrorMessage(bindingResult);
            throw new EntityNotCreatedException(errorMessage);
        }
        D2 responseDto = crudService.create(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти по id")
    public ResponseEntity<D2> get(@PathVariable ID id) {
        D2 responseDto = crudService.get(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить по id")
    public ResponseEntity<D2> update(@PathVariable ID id, @RequestBody @Valid D1 requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = BindingResultUtil.getErrorMessage(bindingResult);
            throw new EntityNotUpdatedException(errorMessage);
        }
        D2 responseDto = crudService.update(id, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить по id")
    public ResponseEntity<HttpStatus> deleteRoom(@PathVariable ID id) {
        crudService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
