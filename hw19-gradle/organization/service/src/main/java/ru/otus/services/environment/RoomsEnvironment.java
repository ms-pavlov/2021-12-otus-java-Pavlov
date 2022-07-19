package ru.otus.services.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.RoomsRequest;
import ru.otus.dto.responses.RoomsResponse;
import ru.otus.jpa.entities.Rooms;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.models.organization.RoomsModel;

@Component
public class RoomsEnvironment extends ModelEnvironmentImpl<Rooms, RoomsModel, RoomsResponse, RoomsRequest> {
    @Autowired
    public RoomsEnvironment(JpaRepository<Rooms, Long> repository,
                            @Qualifier("getValidator") Validator validator,
                            EntityMapper<RoomsModel, Rooms> entityMapper,
                            ResponseMapper<RoomsModel, RoomsResponse> responseMapper,
                            RequestMapper<RoomsModel, RoomsRequest> requestMapper) {
        super(repository, validator, entityMapper, responseMapper, requestMapper);
    }
}
