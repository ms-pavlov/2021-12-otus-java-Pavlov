package ru.otus.services.environment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.responses.BuildingsResponse;
import ru.otus.jpa.entities.Buildings;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;

@Component
public class BuildingsEnvironment extends ModelEnvironmentImpl<Buildings, BuildingsModel, BuildingsResponse, BuildingsRequest> {

    public BuildingsEnvironment(JpaRepository<Buildings, Long> repository,
                                @Qualifier("getValidator") Validator validator,
                                EntityMapper<BuildingsModel, Buildings> entityMapper,
                                ResponseMapper<BuildingsModel, BuildingsResponse> responseMapper,
                                RequestMapper<BuildingsModel, BuildingsRequest> requestMapper) {
        super(repository, validator, entityMapper, responseMapper, requestMapper);
    }
}
