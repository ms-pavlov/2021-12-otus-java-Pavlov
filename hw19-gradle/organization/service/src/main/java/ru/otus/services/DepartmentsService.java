package ru.otus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.dto.responses.DepartmentsResponse;
import ru.otus.jpa.entities.Departments;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.service.RestForCRUDService;
import ru.otus.service.commands.RestCommandFactoryImpl;
import ru.otus.service.strategy.StrategyFactoryWithSoftDelete;

import java.util.Optional;

@Service
public class DepartmentsService extends RestForCRUDService<Departments, DepartmentsModel, DepartmentsResponse, DepartmentsRequest> {
    @Autowired
    public DepartmentsService(ModelEnvironmentImpl<Departments, DepartmentsModel, DepartmentsResponse, DepartmentsRequest> modelEnvironment, ChangeLogService changeLogService) {
        super(modelEnvironment,
                new RestCommandFactoryImpl<>(),
                new StrategyFactoryWithSoftDelete<>(model -> {
                    model.setActive(false);
                    return model;
                }),
                model -> {
                    Optional.of(model)
                            .map(DepartmentsModel::getPlacements)
                            .ifPresent(placementsModels -> placementsModels
                                    .forEach(placement -> changeLogService.send(new PlacementsMessage(placement))));
                    return model;
                });
    }
}
