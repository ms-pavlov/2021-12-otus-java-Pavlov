package ru.otus.service.strategy;

@FunctionalInterface
public interface SoftDeleteMarker<M> {
    M markDeleted(M model);
}
