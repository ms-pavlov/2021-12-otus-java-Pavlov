package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.Validator;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class ModelEnvironmentImplTest {

    private ModelEnvironmentImpl<Object, Object, Object, Object> modelEnvironment;

    @BeforeEach
    void setUp() {
        this.modelEnvironment = ModelEnvironmentImpl.builder()
                .entityMapper(mock(EntityMapper.class))
                .requestMapper(mock(RequestMapper.class))
                .responseMapper(mock(ResponseMapper.class))
                .repository(mock(JpaRepository.class))
                .validator(mock(Validator.class))
                .build();
    }

    @Test
    void getRepository() {
        assertNotNull(modelEnvironment.getRepository());
    }

    @Test
    void getValidator() {
        assertNotNull(modelEnvironment.getValidator());
    }

    @Test
    void getEntityMapper() {
        assertNotNull(modelEnvironment.getEntityMapper());
    }

    @Test
    void getResponseMapper() {
        assertNotNull(modelEnvironment.getResponseMapper());
    }

    @Test
    void getRequestMapper() {
        assertNotNull(modelEnvironment.getRequestMapper());
    }

}