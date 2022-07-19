package ru.otus.mappers;

public interface ResponseMapper<M, R> {

    R toResponse(M model);
}
