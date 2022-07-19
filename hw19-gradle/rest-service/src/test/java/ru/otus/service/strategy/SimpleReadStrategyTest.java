package ru.otus.service.strategy;

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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SimpleReadStrategyTest {
    private final Object MODEL = new Object();

    private ModelEnvironment<Object, Object, Object, Object> modelEnvironment;

    private EntityMapper<Object, Object> entityMapper;
    private JpaRepository<Object, Long> repository;

    @BeforeEach
    void setUp() {
        entityMapper = mock(EntityMapper.class);
        ResponseMapper<Object, Object> responseMapper = mock(ResponseMapper.class);
        RequestMapper<Object, Object> requestMapper = mock(RequestMapper.class);
        repository = mock(JpaRepository.class);
        ModelProcessor<Object> modelProcessor = mock(ModelProcessor.class);
        Validator validator = mock(Validator.class);

        when(entityMapper.toEntity(MODEL)).thenReturn(MODEL);
        when(repository.findById(1L)).thenReturn(Optional.of(MODEL));
        when(entityMapper.toModel(MODEL)).thenReturn(MODEL);
        when(responseMapper.toResponse(MODEL)).thenReturn(MODEL);
        when(requestMapper.createModel(MODEL)).thenReturn(MODEL);
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
        var readStrategy = new SimpleReadStrategy<>();

        var result = readStrategy.execute(1L, modelEnvironment);

        assertTrue(result.isPresent());
        assertEquals(MODEL, result.get());

        verify(entityMapper, times(1)).toModel(MODEL);
        verify(repository, times(1)).findById(1L);

        result = readStrategy.execute(2L, modelEnvironment);
        assertTrue(result.isEmpty());
    }
}