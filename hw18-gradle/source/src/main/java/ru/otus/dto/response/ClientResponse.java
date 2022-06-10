package ru.otus.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientResponse implements Serializable {
    private final Long id;
    private final String name;

    public ClientResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
