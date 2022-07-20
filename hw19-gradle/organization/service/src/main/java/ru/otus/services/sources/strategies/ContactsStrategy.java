package ru.otus.services.sources.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.ContactsRequest;
import ru.otus.mappers.RequestMapper;
import ru.otus.models.organization.ContactsModel;
import ru.otus.service.strategy.SimpleRequestStrategy;

@Component
public class ContactsStrategy extends SimpleRequestStrategy<ContactsModel, ContactsRequest> {
    @Autowired
    public ContactsStrategy(@Qualifier("getValidator") Validator validator,
                            RequestMapper<ContactsModel, ContactsRequest> requestMapper) {
        super(validator, requestMapper);
    }
}
