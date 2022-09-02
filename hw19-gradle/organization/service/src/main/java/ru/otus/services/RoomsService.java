package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.RoomsRequest;
import ru.otus.dto.responses.RoomsResponse;
import ru.otus.models.organization.RoomsModel;
import ru.otus.service.RestForCRUDService;

@Service
public class RoomsService extends RestForCRUDService<RoomsModel, RoomsResponse, RoomsRequest> {
    public RoomsService(ModelEnvironmentImpl< RoomsModel, RoomsRequest> modelEnvironment) {
        super(modelEnvironment);
    }
}
