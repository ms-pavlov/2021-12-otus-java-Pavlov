package ru.otus.mappers.requests;

import org.mapstruct.*;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.mappers.RequestMapper;
import ru.otus.models.organization.BuildingsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PlacementsRequestMapper.class})
public abstract class BuildingsRequestMapper implements RequestMapper<BuildingsModel, BuildingsRequest> {
    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "placements", ignore = true)
    })
    public abstract void updateModel(@MappingTarget BuildingsModel model, BuildingsRequest request);

    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "placements", ignore = true)
    })
    public abstract BuildingsModel createModel(BuildingsRequest request);
}
