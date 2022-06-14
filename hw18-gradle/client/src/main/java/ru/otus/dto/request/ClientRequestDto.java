package ru.otus.dto.request;

import lombok.Data;

@Data
public class ClientRequestDto {
    private final String name;
    private final Integer orderColumn;
}
