package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.responses.BuildingsResponse;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.service.RestForCRUDService;

@Service
public class BuildingsService extends RestForCRUDService<BuildingsModel, BuildingsResponse, BuildingsRequest> {
    private static final Logger log = LoggerFactory.getLogger(BuildingsService.class);

    @Autowired
    public BuildingsService(ModelEnvironmentImpl<BuildingsModel, BuildingsRequest> modelEnvironment) {
        super(modelEnvironment);
    }
}
