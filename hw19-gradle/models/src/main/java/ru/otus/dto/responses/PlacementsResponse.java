package ru.otus.dto.responses;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class PlacementsResponse implements Serializable {
    private static final String NAME_FORMAT = "%s (%s)";

    private Long id;
    private boolean active;

    private Long buildingId;
    private String buildingName;

    private Long departmentId;
    private String departmentName;

    private Collection<ContactsResponse> contacts;
    private Collection<RoomsResponse> rooms;

    public String getPlacementsName() {
        return String.format(NAME_FORMAT, getDepartmentName(), getBuildingName());
    }
}
