package ru.otus.service.strategy;

import ru.otus.ModelEnvironment;

import java.util.Optional;

@FunctionalInterface
public interface ReadStrategy<E, M, R, Q> {
    Optional<M> execute(Long id, ModelEnvironment<E, M, R, Q> modelEnvironment);
}

