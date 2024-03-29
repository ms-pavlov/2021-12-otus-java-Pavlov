package ru.otus.mappers.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.jpa.entities.Contacts;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.qualifiers.SubMapper;
import ru.otus.models.organization.ContactsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PlacementsEntitySubMapper.class})
public abstract class ContactsEntityMapper implements EntityMapper<ContactsModel, Contacts> {
    @Override
    @Mappings({
            @Mapping(target = "placement", source = "model.placement")
    })
    public abstract Contacts toEntity(ContactsModel model);

    @Override
    @Mappings({
            @Mapping(target = "placement", source = "entity.placement")
    })
    public abstract ContactsModel toModel(Contacts entity);
}
