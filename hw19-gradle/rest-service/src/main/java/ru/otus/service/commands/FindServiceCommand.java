package ru.otus.service.commands;

import ru.otus.ModelEnvironment;
import ru.otus.service.ModelProcessor;

import java.util.Optional;

public class FindServiceCommand<E, M, R, Q> implements ServiceCommand<E, M, R, Q> {
    @Override
    public R execute(ModelEnvironment<E, M, R, Q> modelEnvironment,
                     ModelProcessor<M> modelProcessor,
                     Optional<M> modelSource) {
        return modelSource
                .map(modelProcessor::process)
                .map(model -> modelEnvironment.getResponseMapper().toResponse(model))
                .orElse(null);
    }
}
