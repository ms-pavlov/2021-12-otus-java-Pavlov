package ru.otus.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.scheduler.Scheduler;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.services.ClientService;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ClientsControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ClientsControllerTest.class);
    private static final ClientRequestDto CLIENT_1 = new ClientRequestDto("Vasa", 2);
    private static final ClientRequestDto CLIENT_2 = new ClientRequestDto("Kolya", 1);
    private static final ClientResponseDto CLIENT_RESPONSE1 = new ClientResponseDto(1L, "Vasa");
    private static final ClientResponseDto CLIENT_RESPONSE2 = new ClientResponseDto(2L, "Kolya");
    private ClientsController controller;

    @MockBean
    private ClientService clientService;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private Scheduler timer;

    @BeforeEach
    void setUp() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        this.controller = new ClientsController(clientService, executor, timer);
    }

    @Test
    void findAll() {
        when(clientService.findAll()).thenReturn(List.of(CLIENT_RESPONSE1, CLIENT_RESPONSE2));

        var result = controller.findAll()
                .take(2)
                .doOnNext(clients -> log.info("result {}", clients))
                .collectList()
                .block();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).contains(CLIENT_RESPONSE1));
        assertTrue(result.get(0).contains(CLIENT_RESPONSE2));
    }

    @Test
    void create() {
        when(clientService.create(CLIENT_1)).thenReturn(CLIENT_RESPONSE1);
        assertEquals(CLIENT_RESPONSE1, controller.create(CLIENT_1).block());
    }

    @Test
    void update() {
        when(clientService.update(1L, CLIENT_1)).thenReturn(CLIENT_RESPONSE1);
        assertEquals(CLIENT_RESPONSE1, controller.update(1L, CLIENT_1).block());
    }

    @Test
    void findAllWebTest() throws JsonProcessingException {
        var clients =List.of(CLIENT_RESPONSE1, CLIENT_RESPONSE2);
        when(clientService.findAll()).thenReturn(clients);

        log.info("request start");
        var result = get("/api/clients/all/")
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody()
                .take(1)
                .collectList()
                .block();

        assertNotNull(result);
        assertEquals(1, result.size());
        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.writeValueAsString(clients), result.get(0));
    }

    @Test
    void createWebTest() {
        when(clientService.create(CLIENT_2)).thenReturn(CLIENT_RESPONSE1);

        var result = post("/api/clients/")
                .body(BodyInserters.fromValue(CLIENT_2))
                .exchange()
                .expectBody(ClientResponseDto.class)
                .returnResult()
                .getResponseBody();

        verify(clientService, times(1)).create(CLIENT_2);

        assertNotNull(result);
        assertEquals(CLIENT_RESPONSE1, result);
    }

    @Test
    void updateWebTest() {
        when(clientService.update(1L, CLIENT_2)).thenReturn(CLIENT_RESPONSE1);

        var result = put("/api/clients/1/")
                .body(BodyInserters.fromValue(CLIENT_2))
                .exchange()
                .expectBody(ClientResponseDto.class)
                .returnResult()
                .getResponseBody();

        verify(clientService, times(1)).update(1L, CLIENT_2);

        assertNotNull(result);
        assertEquals(CLIENT_RESPONSE1, result);
    }

    private WebTestClient.RequestHeadersSpec<?> get(String url) {
        return webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_NDJSON);
    }

    private WebTestClient.RequestBodySpec post(String url) {
        return webTestClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_NDJSON);
    }

    private WebTestClient.RequestBodySpec put(String url) {
        return webTestClient.put()
                .uri(url)
                .accept(MediaType.APPLICATION_NDJSON);
    }

}