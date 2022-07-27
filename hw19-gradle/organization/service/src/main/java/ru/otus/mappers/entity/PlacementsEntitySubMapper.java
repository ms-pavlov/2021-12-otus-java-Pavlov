package ru.otus.mappers.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.jpa.entities.Placements;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.qualifiers.SubMapper;
import ru.otus.models.organization.PlacementsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {BuildingsEntitySubMapper.class, DepartmentsEntitySubMapper.class, RoomsEntitySubMapper.class})
public abstract class PlacementsEntitySubMapper implements EntityMapper<PlacementsModel, Placements> {

    @Mappings({
            @Mapping(target = "department", source = "model.building"),
            @Mapping(target = "building", source = "model.building"),
            @Mapping(target = "contacts", ignore = true),
            @Mapping(target = "rooms", ignore = true)
    })
    public abstract Placements toEntity(PlacementsModel model);

    @Mappings({
            @Mapping(target = "department", source = "entity.department"),
            @Mapping(target = "building", source = "entity.building"),
            @Mapping(target = "contacts", ignore = true),
            @Mapping(target = "rooms", source = "entity.rooms")
    })
    public abstract PlacementsModel toModel(Placements entity);
}
