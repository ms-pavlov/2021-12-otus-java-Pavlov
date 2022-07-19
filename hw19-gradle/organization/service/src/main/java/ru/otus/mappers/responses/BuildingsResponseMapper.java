package ru.otus.mappers.responses;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.dto.responses.BuildingsResponse;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.mappers.ResponseMapper;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PlacementsResponseMapper.class})
public abstract class BuildingsResponseMapper implements ResponseMapper<BuildingsModel, BuildingsResponse> {
    @Override
    public abstract BuildingsResponse toResponse(BuildingsModel model);

}
