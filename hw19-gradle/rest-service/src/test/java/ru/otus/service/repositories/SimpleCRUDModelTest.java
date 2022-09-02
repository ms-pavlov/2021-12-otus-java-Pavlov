package ru.otus.service.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mappers.EntityMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleCRUDModelTest {
    private final Object MODEL = new Object();

    private final JpaRepository<Object, Long> repository = mock(JpaRepository.class);
    private final EntityMapper<Object, Object> entityMapper= mock(EntityMapper.class);

    private SimpleCRUDModel<Object, Object> crudModel;

    @BeforeEach
    void singUp() {
        crudModel = new SimpleCRUDModel<>(repository, entityMapper);
    }

    @Test
    void findAll() {
        var list = List.of(MODEL, MODEL);

        when(entityMapper.toModel(MODEL)).thenReturn(MODEL);
        when(repository.findAll()).thenReturn(list);

        var result = crudModel.findAll();

        assertEquals(list, result);
    }

    @Test
    void findPageable() {
        var page = new PageImpl<>(
                List.of(MODEL, MODEL),
                PageRequest.of(0, 5),
                2);

        when(entityMapper.toModel(MODEL)).thenReturn(MODEL);
        when(repository.findAll(PageRequest.of(0, 5))).thenReturn(page);

        var result = crudModel.findPageable(PageRequest.of(0, 5));

        assertEquals(page, result);
    }

    @Test
    void findOne() {
        when(entityMapper.toModel(MODEL)).thenReturn(MODEL);
        when(repository.findById(1L)).thenReturn(Optional.of(MODEL));

        var result = crudModel.findOne(1L);

        assertEquals(MODEL, result.orElse(null));
    }

    @Test
    void save() {
        when(entityMapper.toModel(MODEL)).thenReturn(MODEL);
        when(entityMapper.toEntity(MODEL)).thenReturn(MODEL);
        when(repository.save(MODEL)).thenReturn(MODEL);

        var result = crudModel.save(MODEL);

        assertEquals(MODEL, result);
    }
}