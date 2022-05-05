package ru.otus.jdbc.crm.service;

import ru.otus.jdbc.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBService<E> {

    E save(E client);

    Optional<E> findOne(long id);

    List<E> findAll();
}
