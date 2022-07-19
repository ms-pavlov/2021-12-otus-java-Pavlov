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
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.service.commands.RestCommandFactoryImpl;
import ru.otus.service.strategy.StrategyFactoryWithSoftDelete;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestForCRUDServiceTest {
    private final Object MODEL = new Object();
    private RestForCRUDService<Object, Object, Object, Object> crudService;

    @Mock
    private JpaRepository<Object, Long> repository;
    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        this.repository = mock(JpaRepository.class);
        this.bindingResult = mock(BindingResult.class);

        ModelEnvironment<Object, Object, Object, Object> modelEnvironment = mock(ModelEnvironment.class);
        EntityMapper<Object, Object> entityMapper = mock(EntityMapper.class);
        ResponseMapper<Object, Object> responseMapper = mock(ResponseMapper.class);
        RequestMapper<Object, Object> requestMapper = mock(RequestMapper.class);
        var commandFactory = new RestCommandFactoryImpl<>();
        var strategyFactory = new StrategyFactoryWithSoftDelete<>(model -> model);
        Validator validator = mock(Validator.class);


        when(bindingResult.hasErrors()).thenReturn(false);
        when(entityMapper.toModel(MODEL)).thenReturn(MODEL);
        when(entityMapper.toEntity(MODEL)).thenReturn(MODEL);
        when(responseMapper.toResponse(MODEL)).thenReturn(MODEL);
        when(requestMapper.createModel(MODEL)).thenReturn(MODEL);

        when(modelEnvironment.getValidator()).thenReturn(validator);
        when(modelEnvironment.getRepository()).thenReturn(repository);
        when(modelEnvironment.getEntityMapper()).thenReturn(entityMapper);
        when(modelEnvironment.getRequestMapper()).thenReturn(requestMapper);
        when(modelEnvironment.getResponseMapper()).thenReturn(responseMapper);
        this.crudService = new RestForCRUDService<>(modelEnvironment, commandFactory, strategyFactory, model -> model);
    }

    @Test
    void findAll() {
        var list = List.of(MODEL, MODEL);

        when(repository.findAll()).thenReturn(list);

        var result = crudService.findAll();

        assertEquals(list, result);
    }

    @Test
    void findPageable() {
        var page = new PageImpl<> (
                List.of(MODEL, MODEL),
                PageRequest.of(0, 5),
                2);

        when(repository.findAll(PageRequest.of(0, 5))).thenReturn(page);

        var result = crudService.findPageable(PageRequest.of(0, 5));

        assertEquals(page, result);
    }

    @Test
    void findOne() {
        when(repository.findById(1L)).thenReturn(Optional.of(MODEL));

        var result = crudService.findOne(1L);

        assertEquals(MODEL, result);
    }

    @Test
    void create() {
        when(repository.save(MODEL)).thenReturn(MODEL);

        var result = crudService.create(MODEL);

        verify(repository, times(1)).save(MODEL);

        assertEquals(MODEL, result);
    }

    @Test
    void update() {
        when(repository.findById(1L)).thenReturn(Optional.of(MODEL));
        when(repository.save(MODEL)).thenReturn(MODEL);

        var result = crudService.update(1L, MODEL);

        verify(repository, times(1)).save(MODEL);

        assertEquals(MODEL, result);
    }

    @Test
    void delete() {
        when(repository.findById(1L)).thenReturn(Optional.of(MODEL));
        when(repository.save(MODEL)).thenReturn(MODEL);

        var result = crudService.delete(1L);

        verify(repository, times(1)).save(MODEL);

        assertEquals(MODEL, result);
    }
}