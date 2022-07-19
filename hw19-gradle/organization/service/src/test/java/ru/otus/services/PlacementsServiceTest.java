package ru.otus.services;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import ru.otus.dto.requests.PlacementsRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class PlacementsServiceTest {
    private static final Logger log = LoggerFactory.getLogger(PlacementsServiceTest.class);

    private final static int COUNT = 20;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    private final static PlacementsRequest REQUEST = PlacementsRequest.builder()
            .active(true)
            .departmentId(1L)
            .buildingId(1L)
            .build();
    private final static PlacementsRequest EMPTY_REQUEST = PlacementsRequest.builder()
            .departmentId(1L)
            .buildingId(1L)
            .build();

    @Autowired
    private PlacementsService service;

    @Test
    void findAll() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        var size = service.findAll().size();

        for (var i = 0; i < COUNT; i++) {
            var result = service.create(REQUEST);
            assertNotNull(result);
            assertNotNull(result.getId());
        }

        var list = service.findAll();

        assertEquals(COUNT+size, list.size());
    }

    @Test
    void findPageable() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        var size = service.findAll().size();

        for (var i = 0; i < COUNT; i++) {
            var result = service.create(REQUEST);
            assertNotNull(result);
            assertNotNull(result.getId());
        }

        var page = service.findPageable(PageRequest.of(0, 5));

        assertEquals(COUNT+size, page.getTotalElements());
        assertEquals(5, page.getContent().size());

        page.getContent().forEach(buildingsResponse -> {
            assertNotNull(buildingsResponse);
            assertNotNull(buildingsResponse.getId());
        });

    }

    @Test
    void createAndFind() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        var result = service.create(REQUEST);

        assertNotNull(result);
        assertNotNull(result.getId());

        var found = service.findOne(result.getId());
        assertNotNull(found);
        assertTrue(found.isActive());
    }

    @Test
    void update() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        var create = service.create(EMPTY_REQUEST);

        assertNotNull(create);
        assertNotNull(create.getId());

        var update = service.update(create.getId(), REQUEST);

        var found = service.findOne(update.getId());
        assertNotNull(found);
        assertTrue(found.isActive());
    }

    @Test
    void delete() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        var result = service.create(REQUEST);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertTrue(result.isActive());

        var delete = service.delete(result.getId());

        log.info("delete {}", delete);

        var found = service.findOne(result.getId());
        assertNotNull(found);
        assertFalse(found.isActive());
    }
}