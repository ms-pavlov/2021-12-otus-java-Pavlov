package ru.otus.services.sources.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.PlacementsRequest ;
import ru.otus.mappers.RequestMapper;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.service.strategy.SimpleRequestStrategy;

@Component
public class PlacementsStrategy extends SimpleRequestStrategy<PlacementsModel, PlacementsRequest> {
    @Autowired
    public PlacementsStrategy(@Qualifier("getValidator") Validator validator,
                              RequestMapper<PlacementsModel, PlacementsRequest> requestMapper) {
        super(validator, requestMapper);
    }
}