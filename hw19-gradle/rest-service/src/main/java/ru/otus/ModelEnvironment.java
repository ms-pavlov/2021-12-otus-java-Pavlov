package ru.otus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.Validator;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;

public interface ModelEnvironment<E, M, R, Q>{

    JpaRepository<E, Long> getRepository();

    Validator getValidator();

    EntityMapper<M, E> getEntityMapper();

    ResponseMapper<M, R> getResponseMapper();

    RequestMapper<M, Q> getRequestMapper();
}
