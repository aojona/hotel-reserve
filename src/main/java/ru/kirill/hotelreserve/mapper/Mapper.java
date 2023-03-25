package ru.kirill.hotelreserve.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class Mapper<E,D> {

    private final ModelMapper modelMapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    public D mapToDto(E entity) {
        return modelMapper.map(entity, dtoClass);
    }

    public E mapToEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    public void updateEntity(D dtoSource, E entityToUpdate) {
        modelMapper.map(dtoSource, entityToUpdate);
    }
}
