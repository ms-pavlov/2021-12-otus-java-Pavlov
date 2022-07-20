package ru.otus.services.sources.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.RoomsRequest ;
import ru.otus.mappers.RequestMapper;
import ru.otus.models.organization.RoomsModel;
import ru.otus.service.strategy.SimpleRequestStrategy;

@Component
public class RoomsStrategy extends SimpleRequestStrategy<RoomsModel, RoomsRequest> {
    @Autowired
    public RoomsStrategy(@Qualifier("getValidator") Validator validator,
                         RequestMapper<RoomsModel, RoomsRequest> requestMapper) {
        super(validator, requestMapper);
    }
}