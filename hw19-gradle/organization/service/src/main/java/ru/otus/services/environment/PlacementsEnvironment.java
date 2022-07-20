package ru.otus.services.environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.controllers.rest.ChangeLogController;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.requests.PlacementsRequest;
import ru.otus.dto.responses.PlacementsResponse;
import ru.otus.jpa.entities.Placements;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.service.repositories.CRUDModel;
import ru.otus.service.strategy.RequestStrategy;
import ru.otus.services.ChangeLogService;

@Component
public class PlacementsEnvironment extends ModelEnvironmentImpl<PlacementsModel, PlacementsRequest> {
    private static final Logger log = LoggerFactory.getLogger(PlacementsEnvironment.class);
    @Autowired
    public PlacementsEnvironment(RequestStrategy<PlacementsModel, PlacementsRequest> requestStrategy,
                                 CRUDModel<PlacementsModel> dataSource,
                                 ChangeLogService changeLogService) {
        super(model -> {
                    log.info("message {}", model);
                    changeLogService.send(new PlacementsMessage(model));
                    return model;
                },
                requestStrategy,
                dataSource,
                model -> {
                    model.setActive(false);
                    return model;
                });
    }
}
