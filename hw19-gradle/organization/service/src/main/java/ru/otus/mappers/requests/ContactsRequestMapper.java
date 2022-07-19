package ru.otus.mappers.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.dto.requests.ContactsRequest;
import ru.otus.jpa.repositories.PlacementsRepository;
import ru.otus.mappers.entity.PlacementsEntityMapper;
import ru.otus.models.organization.ContactsModel;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.mappers.RequestMapper;

import java.util.Optional;

@Component
public class ContactsRequestMapper implements RequestMapper<ContactsModel, ContactsRequest> {
    private final PlacementsRepository placementsRepository;
    private final PlacementsEntityMapper placementsEntityMapper;

    @Autowired
    public ContactsRequestMapper(PlacementsRepository placementsRepository, PlacementsEntityMapper placementsEntityMapper) {
        this.placementsRepository = placementsRepository;
        this.placementsEntityMapper = placementsEntityMapper;
    }

    private Optional<PlacementsModel> findPlacements(Long id) {
        return placementsRepository.findById(id).map(placementsEntityMapper::toModel);
    }


    @Override
    public void updateModel(ContactsModel model, ContactsRequest request) {
        if (request == null) {
            return;
        }

        Optional.ofNullable(request.getName())
                .ifPresent(model::setName);
        Optional.ofNullable(request.getPhone())
                .ifPresent(model::setPhone);
        model.setActive(request.isActive());
        findPlacements(request.getPlacementId())
                .ifPresent(model::setPlacement);
    }

    @Override
    public ContactsModel createModel(ContactsRequest request) {
        if (request == null) {
            return null;
        }

        var model = ContactsModel.builder();

        Optional.ofNullable(request.getName())
                .ifPresent(model::name);
        Optional.ofNullable(request.getPhone())
                .ifPresent(model::phone);
        model.active(request.isActive());
        findPlacements(request.getPlacementId())
                .ifPresent(model::placement);

        return model.build();
    }
}
