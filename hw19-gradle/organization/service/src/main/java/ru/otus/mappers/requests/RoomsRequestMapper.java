package ru.otus.mappers.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.dto.requests.RoomsRequest;
import ru.otus.jpa.repositories.PlacementsRepository;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.entity.PlacementsEntityMapper;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.models.organization.RoomsModel;

import java.util.Optional;

@Component
public class RoomsRequestMapper implements RequestMapper<RoomsModel, RoomsRequest> {
    private final PlacementsRepository placementsRepository;
    private final PlacementsEntityMapper placementsEntityMapper;

    @Autowired
    public RoomsRequestMapper(PlacementsRepository placementsRepository, PlacementsEntityMapper placementsEntityMapper) {
        this.placementsRepository = placementsRepository;
        this.placementsEntityMapper = placementsEntityMapper;
    }

    private Optional<PlacementsModel> findPlacements(Long id) {
        return placementsRepository.findById(id).map(placementsEntityMapper::toModel);
    }

    @Override
    public void updateModel(RoomsModel model, RoomsRequest request) {
        if (request == null) {
            return;
        }

        Optional.ofNullable(request.getName())
                .ifPresent(model::setName);
        Optional.ofNullable(request.getDescription())
                .ifPresent(model::setDescription);
        model.setActive(request.isActive());
        findPlacements(request.getPlacementId())
                .ifPresent(model::setPlacement);
    }

    @Override
    public RoomsModel createModel(RoomsRequest request) {
        if (request == null) {
            return null;
        }

        var model = RoomsModel.builder();
        Optional.ofNullable(request.getName())
                .ifPresent(model::name);
        Optional.ofNullable(request.getDescription())
                .ifPresent(model::description);
        model.active(request.isActive());
        findPlacements(request.getPlacementId())
                .ifPresent(model::placement);

        return model.build();
    }
}
