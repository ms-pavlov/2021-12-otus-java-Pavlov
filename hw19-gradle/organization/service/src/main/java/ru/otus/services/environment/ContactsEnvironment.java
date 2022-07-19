package ru.otus.services.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.ContactsRequest;
import ru.otus.dto.responses.ContactsResponse;
import ru.otus.jpa.entities.Contacts;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.models.organization.ContactsModel;

@Component
public class ContactsEnvironment extends ModelEnvironmentImpl<Contacts, ContactsModel, ContactsResponse, ContactsRequest> {
    @Autowired
    public ContactsEnvironment(JpaRepository<Contacts, Long> repository,
                               @Qualifier("getValidator") Validator validator,
                               EntityMapper<ContactsModel, Contacts> entityMapper,
                               ResponseMapper<ContactsModel, ContactsResponse> responseMapper,
                               RequestMapper<ContactsModel, ContactsRequest> requestMapper) {
        super(repository, validator, entityMapper, responseMapper, requestMapper);
    }
}
