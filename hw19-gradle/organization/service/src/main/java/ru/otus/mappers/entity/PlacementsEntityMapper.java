package ru.otus.mappers.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.jpa.entities.Placements;
import ru.otus.mappers.qualifiers.SubMapper;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.mappers.EntityMapper;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE,
        uses = {BuildingsEntityMapper.class, DepartmentsEntityMapper.class,
                ContactsEntityMapper.class, RoomsEntityMapper.class})
public abstract class PlacementsEntityMapper implements EntityMapper<PlacementsModel, Placements> {

    @Override
    @Mappings({
            @Mapping(target = "department", source = "model.department", qualifiedBy = {SubMapper.class}),
            @Mapping(target = "building", source = "model.building", qualifiedBy = {SubMapper.class}),
    })
    public abstract Placements toEntity(PlacementsModel model);

    @Override
    @Mappings({
            @Mapping(target = "department", source = "entity.department", qualifiedBy = {SubMapper.class}),
            @Mapping(target = "building", source = "entity.building", qualifiedBy = {SubMapper.class}),
    })
    public abstract PlacementsModel toModel(Placements entity);

    @SubMapper
    @Mappings({
            @Mapping(target = "department", source = "model.department", qualifiedBy = {SubMapper.class}),
            @Mapping(target = "building", source = "model.building", qualifiedBy = {SubMapper.class}),
            @Mapping(target = "contacts", ignore = true),
            @Mapping(target = "rooms", ignore = true)
    })
    public abstract Placements toSubEntity(PlacementsModel model);

    @SubMapper
    @Mappings({
            @Mapping(target = "department", source = "entity.department", qualifiedBy = {SubMapper.class}),
            @Mapping(target = "building", source = "entity.building", qualifiedBy = {SubMapper.class}),
            @Mapping(target = "contacts", ignore = true),
            @Mapping(target = "rooms", ignore = true)
    })
    public abstract PlacementsModel toSubModel(Placements entity);
}
