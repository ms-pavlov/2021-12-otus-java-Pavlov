package ru.otus.service.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironment;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.service.ModelProcessor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SoftDeleteServiceCommandTest {
    private final Object MODEL = new Object();

    private ModelEnvironment<Object, Object, Object, Object> modelEnvironment;

    private EntityMapper<Object, Object> entityMapper;
    private RequestMapper<Object, Object> requestMapper;
    private ResponseMapper<Object, Object> responseMapper;
    private JpaRepository<Object, Long> repository;
    private ModelProcessor<Object> modelProcessor;

    @BeforeEach
    void setUp() {
        entityMapper = mock(EntityMapper.class);
        responseMapper = mock(ResponseMapper.class);
        repository = mock(JpaRepository.class);
        modelProcessor = mock(ModelProcessor.class);

        when(entityMapper.toEntity(MODEL)).thenReturn(MODEL);
        when(repository.save(MODEL)).thenReturn(MODEL);
        when(entityMapper.toModel(MODEL)).thenReturn(MODEL);
        when(responseMapper.toResponse(MODEL)).thenReturn(MODEL);
        when(modelProcessor.process(MODEL)).thenReturn(MODEL);

        modelEnvironment = ModelEnvironmentImpl.builder()
                .validator(mock(Validator.class))
                .entityMapper(entityMapper)
                .responseMapper(responseMapper)
                .requestMapper(requestMapper)
                .repository(repository).build();

    }

    @Test
    void execute() {
        var result = (new SaveServiceCommand<>()).execute(modelEnvironment, modelProcessor, Optional.of(MODEL));

        assertEquals(MODEL, result);

        verify(entityMapper, times(1)).toEntity(MODEL);
        verify(entityMapper, times(1)).toModel(MODEL);
        verify(repository, times(1)).save(MODEL);
        verify(responseMapper, times(1)).toResponse(MODEL);
        verify(modelProcessor, times(1)).process(MODEL);
    }
}