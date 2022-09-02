package ru.otus.models.organization;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Optional;

@Data
@Builder
public class PlacementsModel {
    private static final String NAME_FORMAT = "%s (%s)";

    private Long id;
    private BuildingsModel building;
    private DepartmentsModel department;
    private boolean active;
    Collection<ContactsModel> contacts;
    Collection<RoomsModel> rooms;

    public Long getDepartmentId() {
        return Optional.ofNullable(department).map(DepartmentsModel::getId).orElse(null);
    }

    public String getDepartmentName() {
        return Optional.ofNullable(department).map(DepartmentsModel::getName).orElse(null);
    }

    public String getPlacementsName() {
        return String.format(NAME_FORMAT, getDepartmentName(), getBuildingName());
    }

    public Long getBuildingId() {
        return Optional.ofNullable(building).map(BuildingsModel::getId).orElse(null);
    }

    public String getBuildingName() {
        return Optional.ofNullable(building).map(BuildingsModel::getName).orElse(null);
    }
}
