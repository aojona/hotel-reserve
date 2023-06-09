package ru.kirill.hotelreserve.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kirill.hotelreserve.config.logging.Logging;
import ru.kirill.hotelreserve.dto.ResponseData;
import ru.kirill.hotelreserve.exception.EntityNotCreatedException;
import ru.kirill.hotelreserve.exception.EntityNotUpdatedException;
import ru.kirill.hotelreserve.listener.CreateUpdateEvent;
import ru.kirill.hotelreserve.service.CRUDService;
import ru.kirill.hotelreserve.util.BindingResultUtil;
import java.util.List;

import static ru.kirill.hotelreserve.enums.LayerType.CONTROLLER;

@Logging(value = CONTROLLER)
@RequiredArgsConstructor
@SecurityRequirement(name = "aojonaAuth")
public abstract class CRUDController<E,D1,D2,ID extends Number> {

    private final CRUDService<E,D1,D2,ID> crudService;
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping
    @Operation(summary = "Найти все")
    public ResponseEntity<ResponseData<D2>> getAll(@RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) Integer size) {
        List<D2> all = (page == null || size == null || page < 0 || size <= 0)
                ? crudService.getAll()
                : crudService.getPage(page, size);
        return new ResponseEntity<>(new ResponseData<>(all), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создать")
    @Parameter(name = "lang", allowEmptyValue = true, description = "en/ru")
    public ResponseEntity<D2> create(@RequestBody @Valid D1 requestDto, BindingResult bindingResult,
                                     HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            String errorMessage = BindingResultUtil.getErrorMessage(bindingResult);
            throw new EntityNotCreatedException(errorMessage);
        }
        D2 responseDto = crudService.create(requestDto);
        eventPublisher.publishEvent(new CreateUpdateEvent<>(responseDto, request, "create"));
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
    @Parameter(name = "lang", allowEmptyValue = true, description = "en/ru")
    public ResponseEntity<D2> update(@PathVariable ID id, @RequestBody @Valid D1 requestDto, BindingResult bindingResult,
                                     HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            String errorMessage = BindingResultUtil.getErrorMessage(bindingResult);
            throw new EntityNotUpdatedException(errorMessage);
        }
        D2 responseDto = crudService.update(id, requestDto);
        eventPublisher.publishEvent(new CreateUpdateEvent<>(responseDto, request, "update"));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить по id")
    public ResponseEntity<HttpStatus> deleteRoom(@PathVariable ID id) {
        crudService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
