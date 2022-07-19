package ru.otus.service.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.BindingResult;
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

class SimpleUpdateStrategyTest {
    private final Object MODEL = new Object();

    private ModelEnvironment<Object, Object, Object, Object> modelEnvironment;

    private RequestMapper<Object, Object> requestMapper;
    private BindingResult binding;

    @BeforeEach
    void setUp() {
        EntityMapper<Object, Object> entityMapper = mock(EntityMapper.class);
        ResponseMapper<Object, Object> responseMapper = mock(ResponseMapper.class);
        requestMapper = mock(RequestMapper.class);
        JpaRepository<Object, Long> repository = mock(JpaRepository.class);
        ModelProcessor<Object> modelProcessor = mock(ModelProcessor.class);
        Validator validator = mock(Validator.class);
        binding = mock(BindingResult.class);

        when(entityMapper.toEntity(MODEL)).thenReturn(MODEL);
        when(repository.save(MODEL)).thenReturn(MODEL);
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
    void executeWhenNotFind() {
        var updateStrategy = new SimpleUpdateStrategy<>();

        when(binding.hasErrors()).thenReturn(false);

        var result = updateStrategy.execute(2L, MODEL, modelEnvironment);

        assertTrue(result.isEmpty());

    }

    @Test
    void execute() {
        var updateStrategy = new SimpleUpdateStrategy<>();

        doNothing().when(requestMapper).updateModel(MODEL, MODEL);

        when(binding.hasErrors()).thenReturn(false);

        var result = updateStrategy.execute(1L, MODEL, modelEnvironment);
        assertTrue(result.isPresent());
        assertEquals(MODEL, result.get());

        verify(requestMapper, times(1)).updateModel(MODEL, MODEL);

    }
}