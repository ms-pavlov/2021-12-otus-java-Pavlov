package ru.otus.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClientResponseDto implements Serializable {
    private final Long id;
    private final String name;

    @JsonCreator
    public ClientResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
