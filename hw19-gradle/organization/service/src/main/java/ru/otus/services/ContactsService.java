package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.ContactsRequest;
import ru.otus.dto.responses.ContactsResponse;
import ru.otus.models.organization.ContactsModel;
import ru.otus.service.RestForCRUDService;

@Service
public class ContactsService extends RestForCRUDService<ContactsModel, ContactsResponse, ContactsRequest> {
    public ContactsService(ModelEnvironmentImpl<ContactsModel, ContactsRequest> modelEnvironment) {
        super(modelEnvironment);
    }
}
