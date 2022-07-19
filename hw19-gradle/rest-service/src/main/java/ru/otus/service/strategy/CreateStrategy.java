package ru.otus.service.strategy;

import ru.otus.ModelEnvironment;

import java.util.Optional;

@FunctionalInterface
public interface CreateStrategy<E, M, R, Q> {
    Optional<M> execute(Q request, ModelEnvironment<E, M, R, Q> modelEnvironment);
}
