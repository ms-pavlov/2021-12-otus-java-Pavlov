package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.Validator;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.service.ModelProcessor;
import ru.otus.service.repositories.CRUDModel;
import ru.otus.service.strategy.RequestStrategy;
import ru.otus.service.strategy.SoftDeleteMarker;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class ModelEnvironmentImplTest {

    private ModelEnvironmentImpl<Object, Object> modelEnvironment;

    @BeforeEach
    void setUp() {
        this.modelEnvironment = ModelEnvironmentImpl.builder()
                .afterModifyProcessor(mock(ModelProcessor.class))
                .requestStrategy(mock(RequestStrategy.class))
                .dataSource(mock(CRUDModel.class))
                .deleteMarker(mock(SoftDeleteMarker.class))
                .build();
    }

    @Test
    void getDataSource() {
        assertNotNull(modelEnvironment.getDataSource());
    }

    @Test
    void getAfterModifyProcessor() {
        assertNotNull(modelEnvironment.getAfterModifyProcessor());
    }

    @Test
    void getDeleteMarker() {
        assertNotNull(modelEnvironment.getDeleteMarker());
    }

    @Test
    void getRequestStrategy() {
        assertNotNull(modelEnvironment.getRequestStrategy());
    }

}