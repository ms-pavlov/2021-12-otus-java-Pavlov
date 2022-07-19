package ru.otus.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.responses.BuildingsResponse;
import ru.otus.jpa.entities.Buildings;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.rest.SimpleRestForCRUDController;
import ru.otus.service.RestForCRUDService;

import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/api/buildings/",
        produces = MediaType.APPLICATION_NDJSON_VALUE)
public class BuildingsController extends SimpleRestForCRUDController<Buildings, BuildingsModel, BuildingsResponse, BuildingsRequest> {
    @Autowired
    public BuildingsController(
            RestForCRUDService<Buildings, BuildingsModel, BuildingsResponse, BuildingsRequest> service,
            ExecutorService executor) {
        super(service, executor);
    }
}
