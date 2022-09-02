package ru.otus.services.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.dto.responses.DepartmentsResponse;
import ru.otus.jpa.entities.Departments;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.service.repositories.CRUDModel;
import ru.otus.service.strategy.RequestStrategy;
import ru.otus.services.ChangeLogService;

import java.util.Optional;

@Component
public class DepartmentsEnvironment extends ModelEnvironmentImpl<DepartmentsModel, DepartmentsRequest> {
    @Autowired
    public DepartmentsEnvironment(RequestStrategy<DepartmentsModel, DepartmentsRequest> requestStrategy,
                                  CRUDModel<DepartmentsModel> dataSource,
                                  ChangeLogService changeLogService) {
        super(model -> {
                    Optional.of(model)
                            .map(DepartmentsModel::getPlacements)
                            .ifPresent(placementsModels -> placementsModels
                                    .forEach(placement -> changeLogService.send(new PlacementsMessage(placement))));
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
