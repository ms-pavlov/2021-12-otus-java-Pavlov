package ru.otus.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.dto.request.ClientRequest;
import ru.otus.dto.response.ClientResponse;
import ru.otus.services.ClientService;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest
@ActiveProfiles("test")
class ClientsControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ClientsControllerTest.class);

    private ClientsController controller;
    private Model model;
    private static final ClientRequest CLIENT_1 = new ClientRequest("Vasya", 2);
    private static final ClientRequest CLIENT_2 = new ClientRequest("Kolya", 1);
    private static final ClientResponse CLIENT_RESPONSE1 = new ClientResponse(1L, "Vasya");
    private static final ClientResponse CLIENT_RESPONSE2 = new ClientResponse(2L, "Kolya");
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;
    private WebClient client;

    @BeforeEach
    void setUp() {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        client = WebClient.create("http://localhost:" + wireMockServer.port());

        this.clientService = mock(ClientService.class);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        this.controller = new ClientsController(clientService, executor);
        this.model = mock(Model.class);
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
        assertEquals(CLIENT_RESPONSE1, controller.create(model, CLIENT_1).block());
    }

    @Test
    void update() {
        when(clientService.update(1L, CLIENT_1)).thenReturn(new ClientResponse(1L, "Vasya"));
        assertEquals(CLIENT_RESPONSE1, controller.update(model, 1L, CLIENT_1).block());
    }

    @Test
    void dataTest() {
        when(clientService.findAll()).thenReturn(List.of(CLIENT_RESPONSE1, CLIENT_RESPONSE2));

        var result = WebTestClient
                .bindToController(controller).build().get()
                .uri("/api/clients/all/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(List.class)
                .getResponseBody().blockFirst();


        log.info("result {}", result);
/*        assertNotNull(result);
        assertEquals(2, result.size());*/
    }
}