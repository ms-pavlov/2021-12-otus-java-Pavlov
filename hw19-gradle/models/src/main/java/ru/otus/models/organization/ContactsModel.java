package ru.otus.models.organization;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class ContactsModel {
    private final static String CONTACT_FORMAT = "%s, %s (%s)";

    private Long id;
    private String name;
    private String phone;
    private boolean active;
    private PlacementsModel placement;

    public Long getPlacementId() {
        return Optional.ofNullable(placement).map(PlacementsModel::getId).orElse(null);
    }

    public Long getDepartmentId() {
        return Optional.ofNullable(placement).map(PlacementsModel::getDepartmentId).orElse(null);
    }

    public String getDepartmentName() {
        return Optional.ofNullable(placement).map(PlacementsModel::getDepartmentName).orElse(null);
    }

    public String getContactFormat() {
        return String.format(CONTACT_FORMAT, name, phone, placement.getPlacementsName());
    }

}
