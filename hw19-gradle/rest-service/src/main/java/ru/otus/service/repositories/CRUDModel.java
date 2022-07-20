package ru.otus.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

public interface CRUDModel<M> {

    List<M> findAll();

    Page<M> findPageable(Pageable pageable);

    Optional<M> findOne(Long id);

    M save(M request);
}
