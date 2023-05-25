package ru.kirill.hotelreserve.mapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public abstract class Mapper<E,D> {

    protected final ModelMapper modelMapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    @PostConstruct
    private void setup() {
        modelMapper
                .createTypeMap(entityClass, dtoClass)
                .setPostConverter(toDtoConverter());
        modelMapper
                .createTypeMap(dtoClass, entityClass)
                .setPostConverter(toEntityConverter());
    }

    public D toDto(E entity) {
        return modelMapper.map(entity, dtoClass);
    }

    public E toEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    public void updateEntity(D source, E entityToUpdate) {
        modelMapper.map(source, entityToUpdate);
    }

    private Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapToDto(source, destination);
            return destination;
        };
    }

    protected Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapToEntity(source, destination);
            return destination;
        };
    }

    protected void mapToDto(E source, D destination) {
    }

    protected void mapToEntity(D source, E destination) {
    }
}
