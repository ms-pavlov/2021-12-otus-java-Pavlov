package ru.otus.mappers.responses;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.dto.responses.DepartmentsResponse;
import ru.otus.mappers.ResponseMapper;
import ru.otus.models.organization.DepartmentsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PlacementsResponseMapper.class})
public abstract class DepartmentsResponseMapper implements ResponseMapper<DepartmentsModel, DepartmentsResponse> {
    @Override
    public abstract DepartmentsResponse toResponse(DepartmentsModel model);

}
