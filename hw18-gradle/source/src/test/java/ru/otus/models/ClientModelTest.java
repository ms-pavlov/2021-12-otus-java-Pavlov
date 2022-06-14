package ru.otus.models;

import org.junit.jupiter.api.Test;
import ru.otus.dto.request.ClientRequest;
import ru.otus.entities.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientModelTest {

    @Test
    void toClient() {
        var client = new Client(1L, "Vasa", 0);
        var model = new ClientModel(client);

        assertEquals(client.getId(), model.getId());
        assertEquals(client.getName(), model.getName());
        assertEquals(client.getOrderColumn(), model.getOrder());
    }

    @Test
    void toClientResponse() {
        var request = new ClientRequest("Vasa", 0);
        var model = new ClientModel(1L, request);

        assertEquals(1L, model.getId());
        assertEquals(request.getName(), model.getName());
        assertEquals(request.getOrderColumn(), model.getOrder());
    }
}