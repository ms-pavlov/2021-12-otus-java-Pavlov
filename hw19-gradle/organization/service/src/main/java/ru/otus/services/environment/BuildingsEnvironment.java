package ru.otus.services.environment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.responses.BuildingsResponse;
import ru.otus.jpa.entities.Buildings;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.service.ModelProcessor;
import ru.otus.service.repositories.CRUDModel;
import ru.otus.service.strategy.RequestStrategy;
import ru.otus.service.strategy.SoftDeleteMarker;
import ru.otus.services.ChangeLogService;

import java.util.Optional;

@Component
public class BuildingsEnvironment extends ModelEnvironmentImpl<BuildingsModel, BuildingsRequest> {

    public BuildingsEnvironment(@Qualifier("buildingsStrategy") RequestStrategy<BuildingsModel, BuildingsRequest> requestStrategy,
                                @Qualifier("buildingsSource") CRUDModel<BuildingsModel> dataSource,
                                ChangeLogService changeLogService) {
        super(model -> {
                    Optional.of(model)
                            .map(BuildingsModel::getPlacements)
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
