package ru.otus.mappers.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.jpa.entities.Rooms;
import ru.otus.mappers.EntityMapper;
import ru.otus.models.organization.RoomsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PlacementsEntitySubMapper.class})
public abstract class RoomsEntitySubMapper implements EntityMapper<RoomsModel, Rooms> {
    @Override
    @Mappings({
            @Mapping(target = "placement", ignore = true)
    })
    public abstract Rooms toEntity(RoomsModel model);

    @Override
    @Mappings({
            @Mapping(target = "placement", ignore = true)
    })
    public abstract RoomsModel toModel(Rooms entity);
}
