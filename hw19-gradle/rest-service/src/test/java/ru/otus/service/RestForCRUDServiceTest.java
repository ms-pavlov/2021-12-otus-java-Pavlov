package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironment;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.service.repositories.CRUDModel;
import ru.otus.service.strategy.RequestStrategy;
import ru.otus.service.strategy.SimpleRequestStrategy;
import ru.otus.service.strategy.SoftDeleteMarker;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestForCRUDServiceTest {
    private final Object MODEL = new Object();
    private RestForCRUDService<Object, Object, Object> crudService;

    @Mock
    private CRUDModel<Object> dataSource;
    private RequestStrategy<Object, Object> requestStrategy;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        this.dataSource = mock(CRUDModel.class);
        this.requestStrategy = mock(RequestStrategy.class);

        when(requestStrategy.execute(MODEL)).thenReturn(Optional.of(MODEL));
        when(requestStrategy.execute(MODEL)).thenReturn(Optional.of(MODEL));

        ModelEnvironment<Object, Object> modelEnvironment = new ModelEnvironmentImpl<>(model -> model, requestStrategy, dataSource, model -> model);

        crudService = new RestForCRUDService<>(modelEnvironment);

    }

    @Test
    void findAll() {
        var list = List.of(MODEL, MODEL);

        when(dataSource.findAll()).thenReturn(list);

        var result = crudService.findAll();

        assertEquals(list, result);
    }

    @Test
    void findPageable() {
        var page = new PageImpl<>(
                List.of(MODEL, MODEL),
                PageRequest.of(0, 5),
                2);

        when(dataSource.findPageable(PageRequest.of(0, 5))).thenReturn(page);

        var result = crudService.findPageable(PageRequest.of(0, 5));

        assertEquals(page, result);
    }

    @Test
    void findOne() {
        when(dataSource.findOne(1L)).thenReturn(Optional.of(MODEL));

        var result = crudService.findOne(1L);

        assertEquals(MODEL, result);
    }

    @Test
    void create() {
        when(dataSource.save(MODEL)).thenReturn(MODEL);

        var result = crudService.create(MODEL);

        verify(dataSource, times(1)).save(MODEL);

        assertEquals(MODEL, result);
    }

    @Test
    void update() {
        when(dataSource.findOne(1L)).thenReturn(Optional.of(MODEL));
        when(dataSource.save(MODEL)).thenReturn(MODEL);
        when(requestStrategy.execute(MODEL, MODEL)).thenReturn(Optional.of(MODEL));

        var result = crudService.update(1L, MODEL);

        verify(requestStrategy, times(1))
                .execute(MODEL, MODEL);

        verify(dataSource, times(1)).save(MODEL);

        assertEquals(MODEL, result);
    }

    @Test
    void delete() {
        when(dataSource.findOne(1L)).thenReturn(Optional.of(MODEL));
        when(dataSource.save(MODEL)).thenReturn(MODEL);

        var result = crudService.delete(1L);

        verify(dataSource, times(1)).save(MODEL);

        assertEquals(MODEL, result);
    }
}