package ru.otus.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientRequestDto implements Serializable {
    private final String name;
    private final Integer orderColumn;
}
