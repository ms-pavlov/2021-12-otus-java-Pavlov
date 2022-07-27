package ru.otus.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mappers.EntityMapper;

import java.util.List;
import java.util.Optional;

public class SimpleCRUDModel<M, E> implements CRUDModel<M>{

    private final JpaRepository<E, Long> repository;
    private final EntityMapper<M, E> entityMapper;

    public SimpleCRUDModel(JpaRepository<E, Long> repository, EntityMapper<M, E> entityMapper) {
        this.repository = repository;
        this.entityMapper = entityMapper;
    }


    @Override
    public List<M> findAll() {
        return repository
                .findAll().stream()
                .map(entityMapper::toModel)
                .toList();
    }

    @Override
    public Page<M> findPageable(Pageable pageable) {
        var entities = repository.findAll(pageable);
        return new PageImpl<>(
                entities.stream()
                        .map(entityMapper::toModel)
                        .toList(),
                entities.getPageable(),
                entities.getTotalElements());
    }

    @Override
    public Optional<M> findOne(Long id) {
        return repository.findById(id)
                .map(entityMapper::toModel)
                .or(Optional::empty);
    }

    @Override
    public M save(M request) {
        return Optional.of(repository.save(entityMapper.toEntity(request)))
                .map(entityMapper::toModel)
                .orElse(null);
    }

    @Override
    public JpaRepository<E, Long> getRepository() {
        return repository;
    }

    @Override
    public EntityMapper<M, E> getEntityMapper() {
        return entityMapper;
    }
}
