package ru.otus.services.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.jpa.entities.Placements;
import ru.otus.mappers.EntityMapper;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.service.repositories.SimpleCRUDModel;

@Component
public class PlacementsSource extends SimpleCRUDModel<PlacementsModel, Placements> {
    @Autowired
    public PlacementsSource(JpaRepository<Placements, Long> repository,
                            @Qualifier("placementsEntityMapperImpl") EntityMapper<PlacementsModel, Placements> entityMapper) {
        super(repository, entityMapper);
    }
}
