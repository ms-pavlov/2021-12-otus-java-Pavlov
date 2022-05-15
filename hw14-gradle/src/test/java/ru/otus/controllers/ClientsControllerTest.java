package ru.otus.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.otus.dto.request.ClientRequest;
import ru.otus.dto.response.ClientResponse;
import ru.otus.services.ClientService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientsControllerTest {

    private ClientsController controller;
    private Model model;
    private static final ClientRequest CLIENT_1 = new ClientRequest("Vasya", 2);
    private static final ClientRequest CLIENT_2 = new ClientRequest("Kolya", 1);
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        this.clientService = mock(ClientService.class);
        this.controller = new ClientsController(clientService);
        this.model = mock(Model.class);
    }

    @Test
    void create() {
        when(clientService.create(CLIENT_1)).thenReturn(new ClientResponse(1L, "Vasya"));

        assertEquals("redirect:/", controller.create(model, CLIENT_1));
        assertEquals("redirect:/addclient/", controller.create(model, CLIENT_2));
    }

    @Test
    void update() {
        when(clientService.update(1L, CLIENT_1)).thenReturn(new ClientResponse(1L, "Vasya"));

        assertEquals("redirect:/", controller.update(model, 1L, CLIENT_1));
        assertEquals("redirect:/addclient/", controller.update(model, 1L, CLIENT_2));
    }
}