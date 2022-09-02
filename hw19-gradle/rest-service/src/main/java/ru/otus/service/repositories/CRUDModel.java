package ru.otus.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.Validator;
import ru.otus.mappers.EntityMapper;

import java.util.List;
import java.util.Optional;

public interface CRUDModel<M> {

    List<M> findAll();

    Page<M> findPageable(Pageable pageable);

    Optional<M> findOne(Long id);

    M save(M request);

    JpaRepository getRepository();

    EntityMapper getEntityMapper();
}
