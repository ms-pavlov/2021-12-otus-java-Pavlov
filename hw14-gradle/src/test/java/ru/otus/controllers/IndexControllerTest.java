package ru.otus.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.otus.dto.request.ClientRequest;
import ru.otus.dto.response.ClientResponse;
import ru.otus.services.ClientService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    private IndexController controller;
    private Model model;
    private static final ClientResponse[] CLIENTES = {new ClientResponse(1L, "Vasya"), new ClientResponse(2L, "Kolya")};
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        this.clientService = mock(ClientService.class);
        this.controller = new IndexController(clientService);
        this.model = mock(Model.class);
    }

    @Test
    void showMainPage() {
        when(clientService.findAll()).thenReturn(Arrays.asList(CLIENTES));

        assertEquals("index", controller.showMainPage(model));
        verify(model, times(1)).addAttribute("clients", clientService.findAll());
    }

    @Test
    void showClientForm() {
        when(clientService.findAll()).thenReturn(Arrays.asList(CLIENTES));

        assertEquals("addclient", controller.showClientForm(model));
    }
}