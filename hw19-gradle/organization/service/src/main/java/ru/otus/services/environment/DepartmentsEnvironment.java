package ru.otus.services.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.ModelEnvironmentImpl;
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.dto.responses.DepartmentsResponse;
import ru.otus.jpa.entities.Departments;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.models.organization.DepartmentsModel;

@Component
public class DepartmentsEnvironment extends ModelEnvironmentImpl<Departments, DepartmentsModel, DepartmentsResponse, DepartmentsRequest> {
    @Autowired
    public DepartmentsEnvironment(JpaRepository<Departments, Long> repository,
                                  @Qualifier("getValidator") Validator validator,
                                  @Qualifier("departmentsEntityMapperImpl") EntityMapper<DepartmentsModel, Departments> entityMapper,
                                  ResponseMapper<DepartmentsModel, DepartmentsResponse> responseMapper,
                                  RequestMapper<DepartmentsModel, DepartmentsRequest> requestMapper) {
        super(repository, validator, entityMapper, responseMapper, requestMapper);
    }
}
