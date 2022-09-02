package ru.otus.mappers.responses;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.dto.responses.PlacementsResponse;
import ru.otus.mappers.ResponseMapper;
import ru.otus.models.organization.PlacementsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {BuildingsResponseMapper.class, DepartmentsResponseMapper.class, RoomsResponseMapper.class})
public abstract class PlacementsResponseMapper implements ResponseMapper<PlacementsModel, PlacementsResponse> {
    @Override
    @Mappings({
            @Mapping(target = "departmentId", expression = "java(model.getDepartmentId())"),
            @Mapping(target = "departmentName", expression = "java(model.getDepartmentName())"),
            @Mapping(target = "buildingId", expression = "java(model.getBuildingId())"),
            @Mapping(target = "buildingName", expression = "java(model.getBuildingName())"),
            @Mapping(target = "rooms", source = "model.rooms")
    })
    public abstract PlacementsResponse toResponse(PlacementsModel model);

}
