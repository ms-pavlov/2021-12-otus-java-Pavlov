package ru.otus.models;

import lombok.Data;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.entities.Client;

@Data
public class ClientModel {
    private final Long id;
    private final String name;
    private final Integer order;

    public ClientModel(Long id, String name, Integer orderColumn) {
        this.id = id;
        this.name = name;
        this.order = orderColumn;
    }

    public ClientModel(Client client) {
        this(client.getId(), client.getName(), client.getOrderColumn());
    }

    public ClientModel(Long id, ClientRequestDto client) {
        this(id, client.getName(), client.getOrderColumn());
    }

    public Client toClient() {
        return new Client(this.id, this.name, this.order);
    }

    public ClientResponseDto toClientResponse() {
        return new ClientResponseDto(this.id, this.name);
    }
}
