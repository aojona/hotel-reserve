package ru.kirill.hotelreserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.kirill.hotelreserve.exception.EntityNotFoundException;
import ru.kirill.hotelreserve.mapper.Mapper;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class CRUDService<E,D,ID extends Number> {

    protected final JpaRepository<E,ID> jpaRepository;
    protected final Mapper<E, D> mapper;

    public List<D> getAll() {
        return jpaRepository
                .findAll()
                .stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Transactional
    public D create(D dto) {
        return Optional.of(dto)
                .map(mapper::mapToEntity)
                .map(jpaRepository::save)
                .map(mapper::mapToDto)
                .orElseThrow();
    }

    public D get(ID id) {
        return jpaRepository
                .findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Transactional
    public D update(ID id, D dtoSource) {
        return jpaRepository
                .findById(id)
                .map(entityToUpdate -> {
                    mapper.updateEntity(dtoSource, entityToUpdate);
                    return entityToUpdate;
                })
                .map(jpaRepository::saveAndFlush)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Transactional
    public void delete(ID id) {
        jpaRepository
                .findById(id)
                .map(entity -> {
                    jpaRepository.delete(entity);
                    jpaRepository.flush();
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException(id));
    }
}
