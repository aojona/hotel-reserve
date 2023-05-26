package ru.kirill.hotelreserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.kirill.hotelreserve.exception.EntityNotFoundException;
import ru.kirill.hotelreserve.mapper.Mapper;

import java.net.Authenticator;
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
                .map(mapper::toDto)
                .toList();
    }

    public List<D> getPage(int page, int size) {
        return jpaRepository
                .findAll(PageRequest.of(page, size))
                .map(mapper::toDto)
                .getContent();
    }

    @Transactional
    public D create(D source) {
        return Optional.of(source)
                .map(mapper::toEntity)
                .map(jpaRepository::save)
                .map(mapper::toDto)
                .orElseThrow();
    }

    public D get(ID id) {
        return jpaRepository
                .findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Transactional
    public D update(ID id, D source) {
        return jpaRepository
                .findById(id)
                .map(entityToUpdate -> {
                    mapper.updateEntity(source, entityToUpdate);
                    return entityToUpdate;
                })
                .map(jpaRepository::saveAndFlush)
                .map(mapper::toDto)
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
