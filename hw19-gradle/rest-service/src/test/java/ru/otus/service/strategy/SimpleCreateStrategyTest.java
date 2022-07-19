package ru.otus.service.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.*;
import ru.otus.ModelEnvironment;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.service.ModelProcessor;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SimpleCreateStrategyTest {
    private final Object MODEL = new Object();

    private ModelEnvironment<Object, Object, Object, Object> modelEnvironment;

    private Validator validator;
    private BindingResult binding;

    @BeforeEach
    void setUp() {
        EntityMapper<Object, Object> entityMapper = mock(EntityMapper.class);
        ResponseMapper<Object, Object> responseMapper = mock(ResponseMapper.class);
        RequestMapper<Object, Object> requestMapper = mock(RequestMapper.class);
        JpaRepository<Object, Long> repository = mock(JpaRepository.class);
        ModelProcessor<Object> modelProcessor = mock(ModelProcessor.class);
        validator = mock(Validator.class);
        binding = mock(BindingResult.class);

        when(entityMapper.toEntity(MODEL)).thenReturn(MODEL);
        when(repository.save(MODEL)).thenReturn(MODEL);
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
        var createStrategy = new SimpleCreateStrategy<>();

        doNothing().when(validator).validate(MODEL, binding);

        var result = createStrategy.execute(MODEL, modelEnvironment);
        assertTrue(result.isPresent());
        assertEquals(MODEL, result.get());

    }
}