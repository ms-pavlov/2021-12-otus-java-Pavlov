package ru.otus.services.sources.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.DepartmentsRequest ;
import ru.otus.mappers.RequestMapper;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.service.strategy.SimpleRequestStrategy;

@Component
public class DepartmentsStrategy extends SimpleRequestStrategy<DepartmentsModel, DepartmentsRequest> {
    @Autowired
    public DepartmentsStrategy(@Qualifier("getValidator") Validator validator,
                               RequestMapper<DepartmentsModel, DepartmentsRequest> requestMapper) {
        super(validator, requestMapper);
    }
}
