package ru.otus.service.strategy;

import org.springframework.validation.MapBindingResult;
import ru.otus.ModelEnvironment;

import java.util.HashMap;
import java.util.Optional;

public class SimpleCreateStrategy<E, M, R, Q> implements CreateStrategy<E, M, R, Q> {
    @Override
    public Optional<M> execute(Q request, ModelEnvironment<E, M, R, Q> modelEnvironment) {
        var bindingResult = new MapBindingResult(new HashMap<>(), this.toString());
        modelEnvironment.getValidator().validate(request, bindingResult);
        return bindingResult.hasErrors() ? Optional.empty() : Optional.ofNullable(modelEnvironment.getRequestMapper().createModel(request));
    }
}
