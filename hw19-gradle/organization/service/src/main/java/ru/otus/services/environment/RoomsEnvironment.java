package ru.otus.services.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.requests.RoomsRequest;
import ru.otus.dto.responses.RoomsResponse;
import ru.otus.jpa.entities.Rooms;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.models.organization.RoomsModel;
import ru.otus.service.repositories.CRUDModel;
import ru.otus.service.strategy.RequestStrategy;
import ru.otus.services.ChangeLogService;

import java.util.Optional;

@Component
public class RoomsEnvironment extends ModelEnvironmentImpl<RoomsModel, RoomsRequest> {
    @Autowired
    public RoomsEnvironment(RequestStrategy<RoomsModel, RoomsRequest> requestStrategy,
                            CRUDModel<RoomsModel> dataSource,
                            ChangeLogService changeLogService) {
        super(model -> {
                    Optional.ofNullable(model.getPlacement())
                            .ifPresent(placement -> changeLogService.send(new PlacementsMessage(placement)));
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
