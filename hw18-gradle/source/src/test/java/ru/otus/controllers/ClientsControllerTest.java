package ru.otus.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.otus.dto.request.ClientRequest;
import ru.otus.dto.response.ClientResponse;
import ru.otus.services.ClientService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ClientsControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ClientsControllerTest.class);
    private static final ClientRequest CLIENT_1 = new ClientRequest("Vasya", 2);
    private static final ClientRequest CLIENT_2 = new ClientRequest("Kolya", 1);
    private static final ClientResponse CLIENT_RESPONSE1 = new ClientResponse(1L, "Vasya");
    private static final ClientResponse CLIENT_RESPONSE2 = new ClientResponse(2L, "Kolya");
    private ClientsController controller;

    @MockBean
    private ClientService clientService;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        this.controller = new ClientsController(clientService, executor);
    }

    @Test
    void findAll() {
        when(clientService.findAll()).thenReturn(List.of(CLIENT_RESPONSE1, CLIENT_RESPONSE2));

        var clients = controller.findAll().collectList().block();

        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertEquals(CLIENT_RESPONSE1, clients.get(0));
        assertEquals(CLIENT_RESPONSE2, clients.get(1));
    }

    @Test
    void create() {
        when(clientService.create(CLIENT_1)).thenReturn(new ClientResponse(1L, "Vasya"));
        assertEquals(CLIENT_RESPONSE1, controller.create(CLIENT_1).block());
    }

    @Test
    void update() {
        when(clientService.update(1L, CLIENT_1)).thenReturn(new ClientResponse(1L, "Vasya"));
        assertEquals(CLIENT_RESPONSE1, controller.update(1L, CLIENT_1).block());
    }

    @Test
    void findAllWebTest() {
        when(clientService.findAll()).thenReturn(List.of(CLIENT_RESPONSE1, CLIENT_RESPONSE2));

        var result = get("/api/clients/all/")
                .exchange()
                .expectBodyList(ClientResponse.class)
                .hasSize(2)
                .returnResult()
                .getResponseBody();

        assertNotNull(result);
        assertTrue(result.contains(CLIENT_RESPONSE1));
        assertTrue(result.contains(CLIENT_RESPONSE2));
    }

    @Test
    void createWebTest() {
        when(clientService.create(CLIENT_2)).thenReturn(CLIENT_RESPONSE1);

        var result = post("/api/clients/")
                .body(BodyInserters.fromValue(CLIENT_2))
                .exchange()
                .expectBody(ClientResponse.class)
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
                .expectBody(ClientResponse.class)
                .returnResult()
                .getResponseBody();

        verify(clientService, times(1)).update(1L, CLIENT_2);

        assertNotNull(result);
        assertEquals(CLIENT_RESPONSE1, result);
    }

    private WebTestClient.RequestHeadersSpec<?> get(String url) {
        return webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON);
    }

    private WebTestClient.RequestBodySpec post(String url) {
        return webTestClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON);
    }

    private WebTestClient.RequestBodySpec put(String url) {
        return webTestClient.put()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON);
    }

}