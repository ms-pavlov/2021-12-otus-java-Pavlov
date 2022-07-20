package ru.otus.services.sources.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.mappers.RequestMapper;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.service.strategy.SimpleRequestStrategy;

@Component
public class BuildingsStrategy extends SimpleRequestStrategy<BuildingsModel, BuildingsRequest> {
    @Autowired
    public BuildingsStrategy(@Qualifier("getValidator") Validator validator,
                             RequestMapper<BuildingsModel, BuildingsRequest> requestMapper) {
        super(validator, requestMapper);
    }
}
