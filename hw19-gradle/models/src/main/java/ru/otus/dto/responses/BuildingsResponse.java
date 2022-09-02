package ru.otus.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
@Builder
public class BuildingsResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    Collection<PlacementsResponse> placements;
}
