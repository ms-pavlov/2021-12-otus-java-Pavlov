package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.controllers.rest.ChangeLogController;
import ru.otus.dto.requests.PlacementsRequest;
import ru.otus.dto.responses.PlacementsResponse;
import ru.otus.jpa.entities.Placements;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.service.RestForCRUDService;
import ru.otus.service.commands.RestCommandFactoryImpl;
import ru.otus.service.strategy.StrategyFactoryWithSoftDelete;

@Service
public class PlacementsService extends RestForCRUDService<Placements, PlacementsModel, PlacementsResponse, PlacementsRequest> {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogController.class);

    @Autowired
    public PlacementsService(ModelEnvironmentImpl<Placements, PlacementsModel, PlacementsResponse, PlacementsRequest> modelEnvironment, ChangeLogService changeLogService) {
        super(modelEnvironment,
                new RestCommandFactoryImpl<>(),
                new StrategyFactoryWithSoftDelete<>(model -> {
                    model.setActive(false);
                    return model;
                }),
                model -> {
                    log.info("message {}", model);
                    changeLogService.send(new PlacementsMessage(model));
                    return model;
                });
    }
}
