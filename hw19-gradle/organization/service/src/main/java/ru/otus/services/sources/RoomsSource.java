package ru.otus.services.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.jpa.entities.Rooms;
import ru.otus.mappers.EntityMapper;
import ru.otus.models.organization.RoomsModel;
import ru.otus.service.repositories.SimpleCRUDModel;

@Component
public class RoomsSource extends SimpleCRUDModel<RoomsModel, Rooms> {
    @Autowired
    public RoomsSource(JpaRepository<Rooms, Long> repository,
                       @Qualifier("roomsEntityMapperImpl") EntityMapper<RoomsModel, Rooms> entityMapper) {
        super(repository, entityMapper);
    }
}
