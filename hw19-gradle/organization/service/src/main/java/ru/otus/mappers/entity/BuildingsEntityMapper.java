package ru.otus.mappers.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.jpa.entities.Buildings;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.qualifiers.SubMapper;
import ru.otus.models.organization.BuildingsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PlacementsEntityMapper.class})
public abstract class BuildingsEntityMapper implements EntityMapper<BuildingsModel, Buildings> {

    @Override
    public abstract Buildings toEntity(BuildingsModel model);

    @Override
    public abstract BuildingsModel toModel(Buildings entity);

    @SubMapper
    @Mappings({
            @Mapping(target = "placements", ignore = true)
    })
    public abstract Buildings toSubEntity(BuildingsModel model);

    @SubMapper
    @Mappings({
            @Mapping(target = "placements", ignore = true)
    })
    public abstract BuildingsModel toSubModel(Buildings entity);
}
