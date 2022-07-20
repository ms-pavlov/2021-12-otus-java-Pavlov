package ru.otus.services.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.jpa.entities.Buildings;
import ru.otus.mappers.EntityMapper;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.service.repositories.SimpleCRUDModel;

@Component
public class BuildingsSource extends SimpleCRUDModel<BuildingsModel, Buildings> {
    @Autowired
    public BuildingsSource(JpaRepository<Buildings, Long> repository,
                           @Qualifier("buildingsEntityMapperImpl") EntityMapper<BuildingsModel, Buildings> entityMapper) {
        super(repository, entityMapper);
    }
}
