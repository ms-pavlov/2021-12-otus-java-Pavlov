package ru.otus.mappers;

public interface EntityMapper<M, E> {

    E toEntity(M model);

    M toModel(E entity);
}
