package ru.otus.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestForCRUD<E, M, R, Q> {
    List<R> findAll();

    Page<R> findPageable(Pageable pageable);

    R findOne(Long id);

    R create(Q request);

    R update(Long id, Q request);

    R delete(Long id);
}
