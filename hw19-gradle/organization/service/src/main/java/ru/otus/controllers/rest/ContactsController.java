package ru.otus.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.requests.ContactsRequest;
import ru.otus.dto.responses.ContactsResponse;
import ru.otus.jpa.entities.Contacts;
import ru.otus.models.organization.ContactsModel;
import ru.otus.rest.SimpleRestForCRUDController;
import ru.otus.service.RestForCRUDService;

import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/api/contacts/",
        produces = MediaType.APPLICATION_NDJSON_VALUE)
public class ContactsController extends SimpleRestForCRUDController<Contacts, ContactsModel, ContactsResponse, ContactsRequest> {
    @Autowired
    public ContactsController(RestForCRUDService<Contacts, ContactsModel, ContactsResponse, ContactsRequest> service, ExecutorService executor) {
        super(service, executor);
    }
}
