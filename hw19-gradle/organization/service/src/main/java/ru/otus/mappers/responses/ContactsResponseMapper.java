package ru.otus.mappers.responses;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.dto.responses.ContactsResponse;
import ru.otus.models.organization.ContactsModel;
import ru.otus.mappers.ResponseMapper;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ContactsResponseMapper implements ResponseMapper<ContactsModel, ContactsResponse>  {
    @Override
    @Mappings({
            @Mapping(target = "placementId", expression = "java(model.getPlacementId())")
    })
    public abstract ContactsResponse toResponse(ContactsModel model);
}
