package ru.otus.dto.response;

import lombok.Data;
import ru.otus.models.ClientModel;

import java.util.Optional;

@Data
public class ClientResponse {
    private final Long id;
    private final String name;

    public ClientResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClientResponse(ClientModel client) {
        this(client.getId(), client.getName());
    }
}
