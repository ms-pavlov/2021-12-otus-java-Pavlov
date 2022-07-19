package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.dto.requests.ContactsRequest;
import ru.otus.dto.responses.ContactsResponse;
import ru.otus.jpa.entities.Contacts;
import ru.otus.models.organization.ContactsModel;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.service.RestForCRUDService;
import ru.otus.service.commands.RestCommandFactoryImpl;
import ru.otus.service.strategy.StrategyFactoryWithSoftDelete;

@Service
public class ContactsService extends RestForCRUDService<Contacts, ContactsModel, ContactsResponse, ContactsRequest> {
    public ContactsService(ModelEnvironmentImpl<Contacts, ContactsModel, ContactsResponse, ContactsRequest> modelEnvironment) {
        super(modelEnvironment,
                new RestCommandFactoryImpl<>(),
                new StrategyFactoryWithSoftDelete<>(model -> {
                    model.setActive(false);
                    return model;
                }),
                model -> model);
    }
}
