package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.RoomsRequest;
import ru.otus.dto.responses.RoomsResponse;
import ru.otus.jpa.entities.Rooms;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.RoomsModel;
import ru.otus.service.RestForCRUDService;
import ru.otus.service.commands.RestCommandFactoryImpl;
import ru.otus.service.strategy.StrategyFactoryWithSoftDelete;

import java.util.Optional;

@Service
public class RoomsService extends RestForCRUDService<Rooms, RoomsModel, RoomsResponse, RoomsRequest> {
    public RoomsService(ModelEnvironmentImpl<Rooms, RoomsModel, RoomsResponse, RoomsRequest> modelEnvironment, ChangeLogService changeLogService) {
        super(modelEnvironment,
                new RestCommandFactoryImpl<>(),
                new StrategyFactoryWithSoftDelete<>(model -> {
                    model.setActive(false);
                    return model;
                }),
                model -> {
                    Optional.ofNullable(model.getPlacement())
                            .ifPresent(placement -> changeLogService.send(new PlacementsMessage(placement)));
                    return model;
                });
    }
}
