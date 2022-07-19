package ru.otus.mappers.requests;

import org.mapstruct.*;
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.mappers.RequestMapper;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PlacementsRequestMapper.class})
public abstract class DepartmentsRequestMapper implements RequestMapper<DepartmentsModel, DepartmentsRequest> {
    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "placements", ignore = true)
    })
    public abstract void updateModel(@MappingTarget DepartmentsModel model, DepartmentsRequest request);

    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "placements", ignore = true)
    })
    public abstract DepartmentsModel createModel(DepartmentsRequest request);
}
