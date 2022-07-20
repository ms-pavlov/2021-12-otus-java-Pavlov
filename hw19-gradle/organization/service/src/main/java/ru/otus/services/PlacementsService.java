package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.controllers.rest.ChangeLogController;
import ru.otus.dto.requests.PlacementsRequest;
import ru.otus.dto.responses.PlacementsResponse;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.service.RestForCRUDService;

@Service
public class PlacementsService extends RestForCRUDService<PlacementsModel, PlacementsResponse, PlacementsRequest> {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogController.class);

    @Autowired
    public PlacementsService(ModelEnvironmentImpl<PlacementsModel, PlacementsRequest> modelEnvironment) {
        super(modelEnvironment);
    }
}
