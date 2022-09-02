package ru.otus.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import ru.otus.mappers.ResponseMapper;
import ru.otus.service.RestForCRUDService;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleRestForCRUDControllerTest {
    private static final Object TEST = new Object();

    private SimpleRestForCRUDController<Object, Object, Object> controller;
    private RestForCRUDService<Object, Object, Object> service;

    @BeforeEach
    void setUp() {
        service = mock(RestForCRUDService.class);
        var executorService = Executors.newFixedThreadPool(1,
                new ThreadFactory() {
                    private static final Logger log = LoggerFactory.getLogger(ThreadFactory.class);
                    private final AtomicLong threadIdGenerator = new AtomicLong(0);

                    @Override
                    public Thread newThread(@NonNull Runnable task) {
                        var thread = new Thread(task);
                        thread.setName("test" + threadIdGenerator.incrementAndGet());
                        log.info("Thread {} create", thread.getName());
                        return thread;
                    }
                });

        ResponseMapper<Object, Object> responseMapper = mock(ResponseMapper.class);

        when(responseMapper.toResponse(TEST)).thenReturn(TEST);

        controller = new SimpleRestForCRUDController<>(service, executorService, responseMapper);
    }

    @Test
    void findAll() {
        var list = List.of(TEST, TEST);
        when(service.findAll()).thenReturn(List.of(TEST, TEST));

        var result = controller.findAll().block();
        assertEquals(list, result);

    }

    @Test
    void findPageable() {
        var list = List.of(TEST, TEST);
        var pageRequest = PageRequest.of(0, 5);
        var page = new PageImpl<>(list, pageRequest, list.size());
        when(service.findPageable(pageRequest)).thenReturn(page);

        var result = controller.findPageable(0, 5).block();
        assertEquals(page, result);
    }

    @Test
    void findOne() {
        when(service.findOne(1L)).thenReturn(TEST);

        var result = controller.findOne(1L).block();
        assertEquals(TEST, result);
    }

    @Test
    void create() {
        when(service.create(TEST)).thenReturn(TEST);

        var result = controller.create(TEST).block();
        assertEquals(TEST, result);
    }

    @Test
    void update() {
        when(service.update(1L, TEST)).thenReturn(TEST);

        var result = controller.update(1L, TEST).block();
        assertEquals(TEST, result);
    }

    @Test
    void delete() {
        when(service.delete(1L)).thenReturn(TEST);

        var result = controller.delete(1L).block();
        assertEquals(TEST, result);
    }
}