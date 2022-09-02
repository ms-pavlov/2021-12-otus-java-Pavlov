package ru.otus.mappers.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.jpa.entities.Departments;
import ru.otus.mappers.EntityMapper;
import ru.otus.models.organization.DepartmentsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class DepartmentsEntitySubMapper implements EntityMapper<DepartmentsModel, Departments> {

    @Mappings({
            @Mapping(target = "placements", ignore = true)
    })
    public abstract Departments toEntity(DepartmentsModel model);

    @Mappings({
            @Mapping(target = "placements", ignore = true)
    })
    public abstract DepartmentsModel toModel(Departments entity);
}
