package ru.otus.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.requests.RoomsRequest;
import ru.otus.dto.responses.RoomsResponse;
import ru.otus.jpa.entities.Rooms;
import ru.otus.models.organization.RoomsModel;
import ru.otus.rest.SimpleRestForCRUDController;
import ru.otus.service.RestForCRUDService;

import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/api/rooms/",
        produces = MediaType.APPLICATION_NDJSON_VALUE)
public class RoomsController extends SimpleRestForCRUDController<Rooms, RoomsModel, RoomsResponse, RoomsRequest> {
    @Autowired
    public RoomsController(RestForCRUDService<Rooms, RoomsModel, RoomsResponse, RoomsRequest> service, ExecutorService executor) {
        super(service, executor);
    }
}
