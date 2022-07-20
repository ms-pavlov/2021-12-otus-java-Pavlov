package ru.otus.service.strategy;

import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;
import ru.otus.mappers.RequestMapper;

import java.util.HashMap;
import java.util.Optional;

public class SimpleRequestStrategy<M, Q> implements RequestStrategy<M, Q>{
    private final Validator validator;
    private final RequestMapper<M, Q> requestMapper;

    public SimpleRequestStrategy(Validator validator,
                                 RequestMapper<M, Q> requestMapper) {
        this.validator = validator;
        this.requestMapper = requestMapper;
    }

    @Override
    public Optional<M> execute(M model, Q request) {
        if (validate(request) || null == model) {
            return Optional.empty();
        }
        requestMapper.updateModel(model, request);
        return Optional.of(model);
    }

    @Override
    public Optional<M> execute(Q request) {
        if (validate(request)) {
            return Optional.empty();
        }
        return Optional.of(requestMapper.createModel(request));
    }

    private boolean validate(Q request) {
        var bindingResult = new MapBindingResult(new HashMap<>(), this.toString());
        validator.validate(request, bindingResult);
        return bindingResult.hasErrors();
    }
}
