package ru.otus.service;

@FunctionalInterface
public interface ModelProcessor<M> {
    M process(M model);
}
