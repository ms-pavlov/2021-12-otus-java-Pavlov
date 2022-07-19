package ru.otus.models.organization;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class RoomsModel {
    private final static String ROOM_FORMAT_STRING = "%s, %s";

    private Long id;
    private String name;
    private String description;
    private boolean active;
    private PlacementsModel placement;

    public Long getPlacementId() {
        return Optional.ofNullable(placement).map(PlacementsModel::getId).orElse(null);
    }

    public String getRoomFormat() {
        return String.format(ROOM_FORMAT_STRING, placement.getPlacementsName(), name);
    }
}
