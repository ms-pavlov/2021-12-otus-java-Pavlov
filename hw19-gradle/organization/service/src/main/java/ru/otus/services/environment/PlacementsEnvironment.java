package ru.otus.services.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.PlacementsRequest;
import ru.otus.dto.responses.PlacementsResponse;
import ru.otus.jpa.entities.Placements;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.models.organization.PlacementsModel;

@Component
public class PlacementsEnvironment extends ModelEnvironmentImpl<Placements, PlacementsModel, PlacementsResponse, PlacementsRequest> {
    @Autowired
    public PlacementsEnvironment(JpaRepository<Placements, Long> repository,
                                 @Qualifier("getValidator") Validator validator,
                                 EntityMapper<PlacementsModel, Placements> entityMapper,
                                 ResponseMapper<PlacementsModel, PlacementsResponse> responseMapper,
                                 RequestMapper<PlacementsModel, PlacementsRequest> requestMapper) {
        super(repository, validator, entityMapper, responseMapper, requestMapper);
    }
}
