package ru.otus.service.commands;

import ru.otus.ModelEnvironment;
import ru.otus.service.ModelProcessor;

import java.util.Optional;

@FunctionalInterface
public interface ServiceCommand<E, M, R, Q> {
    R execute(ModelEnvironment<E, M, R, Q> modelEnvironment,
              ModelProcessor<M> modelProcessor,
              Optional<M> modelSource);
}
