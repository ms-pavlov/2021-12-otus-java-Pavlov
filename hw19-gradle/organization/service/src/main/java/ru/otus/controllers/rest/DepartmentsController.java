package ru.otus.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.dto.responses.DepartmentsResponse;
import ru.otus.jpa.entities.Departments;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.rest.SimpleRestForCRUDController;
import ru.otus.service.RestForCRUDService;

import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/api/departments/",
        produces = MediaType.APPLICATION_NDJSON_VALUE)
public class DepartmentsController extends SimpleRestForCRUDController<Departments, DepartmentsModel, DepartmentsResponse, DepartmentsRequest> {
    @Autowired
    public DepartmentsController(RestForCRUDService<Departments, DepartmentsModel, DepartmentsResponse, DepartmentsRequest> service, ExecutorService executor) {
        super(service, executor);
    }
}
