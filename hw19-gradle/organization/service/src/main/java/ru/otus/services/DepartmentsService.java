package ru.otus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.dto.responses.DepartmentsResponse;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.service.RestForCRUDService;

@Service
public class DepartmentsService extends RestForCRUDService<DepartmentsModel, DepartmentsResponse, DepartmentsRequest> {
    @Autowired
    public DepartmentsService(ModelEnvironmentImpl<DepartmentsModel, DepartmentsRequest> modelEnvironment) {
        super(modelEnvironment);
    }
}
