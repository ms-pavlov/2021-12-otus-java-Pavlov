package ru.otus.dto.responses;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomsResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    private Long placementId;
}
