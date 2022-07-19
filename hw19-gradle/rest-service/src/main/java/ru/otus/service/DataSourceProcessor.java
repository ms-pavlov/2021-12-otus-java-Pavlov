package ru.otus.service;

import java.util.Optional;

@FunctionalInterface
public interface DataSourceProcessor<M> {
    Optional<M> process();
}
