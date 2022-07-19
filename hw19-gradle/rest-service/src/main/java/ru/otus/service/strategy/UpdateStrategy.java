package ru.otus.service.strategy;

import ru.otus.ModelEnvironment;

import java.util.Optional;

@FunctionalInterface
public interface UpdateStrategy <E, M, R, Q> {
    Optional<M> execute(Long id, Q request, ModelEnvironment<E, M, R, Q> modelEnvironment);
}