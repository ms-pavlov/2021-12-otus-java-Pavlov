package ru.otus.models.organization;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class DepartmentsModel {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    Collection<PlacementsModel> placements;
}
