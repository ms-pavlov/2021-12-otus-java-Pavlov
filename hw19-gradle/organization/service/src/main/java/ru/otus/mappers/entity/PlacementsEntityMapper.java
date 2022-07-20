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
        uses = {BuildingsEntitySubMapper.class, DepartmentsEntitySubMapper.class,
                ContactsEntityMapper.class, RoomsEntityMapper.class})
public abstract class PlacementsEntityMapper implements EntityMapper<PlacementsModel, Placements> {

    @Override
    @Mappings({
            @Mapping(target = "department", source = "model.department"),
            @Mapping(target = "building", source = "model.building"),
    })
    public abstract Placements toEntity(PlacementsModel model);

    @Override
    @Mappings({
            @Mapping(target = "department", source = "entity.department"),
            @Mapping(target = "building", source = "entity.building"),
    })
    public abstract PlacementsModel toModel(Placements entity);

}
