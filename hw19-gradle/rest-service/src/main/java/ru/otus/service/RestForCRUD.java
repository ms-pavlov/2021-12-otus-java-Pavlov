package ru.otus.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestForCRUD<M, Q> {
    List<M> findAll();

    Page<M> findPageable(Pageable pageable);

    M findOne(Long id);

    M create(Q request);

    M update(Long id, Q request);

    M delete(Long id);
}
