package ru.otus.service.strategy;

import java.util.Optional;

public interface RequestStrategy<M, Q>{
    Optional<M> execute(M model, Q request);

    Optional<M> execute(Q request);
}
