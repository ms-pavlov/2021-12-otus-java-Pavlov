package ru.otus.service.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Validator;
import ru.otus.mappers.RequestMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimpleRequestStrategyTest {

    private final Validator validator = mock(Validator.class);
    private final RequestMapper<Object, Object> requestMapper = mock(RequestMapper.class);

    private final Object MODEL = new Object();

    private SimpleRequestStrategy<Object, Object> requestStrategy;
    @BeforeEach
    void setUp() {

        requestStrategy= new SimpleRequestStrategy<>(validator, requestMapper);
    }

    @Test
    void execute() {
        when(requestMapper.createModel(MODEL)).thenReturn(MODEL);

        var result = requestStrategy.execute(MODEL).orElse(null);

        verify(requestMapper, times(1)).createModel(MODEL);

        assertEquals(MODEL, result);
    }

    @Test
    void testExecute() {
        var result = requestStrategy.execute(MODEL, MODEL).orElse(null);

        verify(requestMapper, times(1)).updateModel(MODEL, MODEL);

        assertEquals(MODEL, result);
    }
}