package ru.otus.mappers.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.jpa.entities.Departments;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.qualifiers.SubMapper;
import ru.otus.models.organization.DepartmentsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PlacementsEntitySubMapper.class})
public abstract class DepartmentsEntityMapper implements EntityMapper<DepartmentsModel, Departments> {

    @Override
    public abstract Departments toEntity(DepartmentsModel model);

    @Override
    public abstract DepartmentsModel toModel(Departments entity);
}
