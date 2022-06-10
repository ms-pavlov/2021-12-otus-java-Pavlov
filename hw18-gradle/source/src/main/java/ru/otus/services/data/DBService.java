package ru.otus.services.data;

import java.util.List;
import java.util.Optional;

public interface DBService<E> {

    E save(E client);

    Optional<E> findOne(long id);

    List<E> findAll();
}
