package ru.otus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.Validator;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;

@Getter
@Builder
@AllArgsConstructor
public class ModelEnvironmentImpl<E, M, R, Q> implements ModelEnvironment<E, M, R, Q> {
    private final JpaRepository<E, Long> repository;
    private final Validator validator;
    private final EntityMapper<M, E> entityMapper;
    private final ResponseMapper<M, R> responseMapper;
    private final RequestMapper<M, Q> requestMapper;
}
