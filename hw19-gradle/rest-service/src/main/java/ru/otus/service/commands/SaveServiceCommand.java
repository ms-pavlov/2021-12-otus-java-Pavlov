package ru.otus.service.commands;

import ru.otus.ModelEnvironment;
import ru.otus.service.ModelProcessor;

import java.util.Optional;

public class SaveServiceCommand<E, M, R, Q> implements ServiceCommand<E, M, R, Q>{
    @Override
    public R execute(ModelEnvironment<E, M, R, Q> modelEnvironment,
                     ModelProcessor<M> modelProcessor,
                     Optional<M> modelSource) {
        return modelSource
                .map(model -> modelEnvironment.getEntityMapper().toEntity(model))
                .map(entity -> modelEnvironment.getRepository().save(entity))
                .map(entity -> modelEnvironment.getEntityMapper().toModel(entity))
                .map(modelProcessor::process)
                .map(model -> modelEnvironment.getResponseMapper().toResponse(model))
                .orElse(null);
    }
}
