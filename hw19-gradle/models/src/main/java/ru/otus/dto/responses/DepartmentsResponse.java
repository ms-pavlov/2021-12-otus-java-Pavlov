package ru.otus.dto.responses;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class DepartmentsResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    Collection<PlacementsResponse> placements;
}
