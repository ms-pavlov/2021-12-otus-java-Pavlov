package ru.otus.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClientResponse implements Serializable {
    private final Long id;
    private final String name;

    @JsonCreator
    public ClientResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
