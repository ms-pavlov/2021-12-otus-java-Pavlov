package ru.otus.mappers.responses;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.dto.responses.RoomsResponse;
import ru.otus.mappers.ResponseMapper;
import ru.otus.models.organization.RoomsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RoomsResponseMapper implements ResponseMapper<RoomsModel, RoomsResponse> {
    @Override
    @Mappings({
            @Mapping(target = "placementId", expression = "java(model.getPlacementId())")
    })
    public abstract RoomsResponse toResponse(RoomsModel model);
}
