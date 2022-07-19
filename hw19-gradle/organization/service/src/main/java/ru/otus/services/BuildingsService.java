package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.responses.BuildingsResponse;
import ru.otus.jpa.entities.Buildings;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.service.RestForCRUDService;
import ru.otus.service.commands.RestCommandFactoryImpl;
import ru.otus.service.strategy.StrategyFactoryWithSoftDelete;

import java.util.Optional;

@Service
public class BuildingsService extends RestForCRUDService<Buildings, BuildingsModel, BuildingsResponse, BuildingsRequest> {
    private static final Logger log = LoggerFactory.getLogger(BuildingsService.class);
    @Autowired
    public BuildingsService(ModelEnvironmentImpl<Buildings, BuildingsModel, BuildingsResponse, BuildingsRequest> modelEnvironment, ChangeLogService changeLogService) {
        super(modelEnvironment,
                new RestCommandFactoryImpl<>(),
                new StrategyFactoryWithSoftDelete<>(model -> {
                    model.setActive(false);
                    return model;
                }),
                model -> {
                    Optional.of(model)
                            .map(BuildingsModel::getPlacements)
                            .ifPresent(placementsModels -> placementsModels
                                    .forEach(placement -> changeLogService.send(new PlacementsMessage(placement))));
                    return model;
                });
    }


}
