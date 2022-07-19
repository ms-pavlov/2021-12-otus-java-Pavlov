package ru.otus.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.otus.dto.requests.PlacementsRequest;
import ru.otus.dto.responses.PlacementsResponse;
import ru.otus.jpa.entities.Placements;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.rest.SimpleMonoMaker;
import ru.otus.rest.SimpleRestForCRUDController;
import ru.otus.service.RestForCRUDService;

import java.util.List;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/api/placements/",
        produces = MediaType.APPLICATION_NDJSON_VALUE)
public class PlacementsController extends SimpleRestForCRUDController<Placements, PlacementsModel, PlacementsResponse, PlacementsRequest> {
    @Autowired
    public PlacementsController(RestForCRUDService<Placements, PlacementsModel, PlacementsResponse, PlacementsRequest> service, ExecutorService executor) {
        super(service, executor);
    }

    @RequestMapping(value = "/as/message", method = RequestMethod.GET)
    public Mono<List<PlacementsMessage>> findAllASMessage() {
        return new SimpleMonoMaker<PlacementsMessage>()
                .makeMonoList(() -> getService().findAll()
                .stream()
                .map(PlacementsMessage::new)
                .toList(), getExecutor());

    }
}
