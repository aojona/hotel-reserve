package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kirill.hotelreserve.dto.ResponseData;
import ru.kirill.hotelreserve.exception.EntityNotCreatedException;
import ru.kirill.hotelreserve.exception.EntityNotUpdatedException;
import ru.kirill.hotelreserve.service.CRUDService;
import ru.kirill.hotelreserve.util.BindingResultUtil;
import java.util.List;

@RequiredArgsConstructor
public abstract class CRUDController<E,D,ID extends Number> {

    private final CRUDService<E,D,ID> crudService;

    @GetMapping
    @Operation(summary = "Найти все")
    public ResponseEntity<ResponseData<D>> getAll(@RequestParam(required = false) int page,
                                                  @RequestParam(required = false) int size) {
        List<D> all = (page < 0 || size <= 0)
                ? crudService.getAll()
                : crudService.getPage(page, size);
        return new ResponseEntity<>(new ResponseData<>(all), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создать")
    public ResponseEntity<D> create(@RequestBody @Valid D requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = BindingResultUtil.getErrorMessage(bindingResult);
            throw new EntityNotCreatedException(errorMessage);
        }
        D responseDto = crudService.create(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти по id")
    public ResponseEntity<D> get(@PathVariable ID id) {
        D responseDto = crudService.get(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить по id")
    public ResponseEntity<D> update(@PathVariable ID id, @RequestBody @Valid D requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = BindingResultUtil.getErrorMessage(bindingResult);
            throw new EntityNotUpdatedException(errorMessage);
        }
        D responseDto = crudService.update(id, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить по id")
    public ResponseEntity<HttpStatus> deleteRoom(@PathVariable ID id) {
        crudService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
