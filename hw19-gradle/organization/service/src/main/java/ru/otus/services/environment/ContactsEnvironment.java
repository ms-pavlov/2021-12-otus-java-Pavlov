package ru.otus.services.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.requests.ContactsRequest;
import ru.otus.dto.responses.ContactsResponse;
import ru.otus.jpa.entities.Contacts;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.models.organization.ContactsModel;
import ru.otus.service.repositories.CRUDModel;
import ru.otus.service.strategy.RequestStrategy;
import ru.otus.services.ChangeLogService;

@Component
public class ContactsEnvironment extends ModelEnvironmentImpl<ContactsModel, ContactsRequest> {
    @Autowired
    public ContactsEnvironment(RequestStrategy<ContactsModel, ContactsRequest> requestStrategy,
                               CRUDModel<ContactsModel> dataSource,
                               ChangeLogService changeLogService) {
        super(model -> model,
                requestStrategy,
                dataSource,
                model -> {
                    model.setActive(false);
                    return model;
                });
    }
}
