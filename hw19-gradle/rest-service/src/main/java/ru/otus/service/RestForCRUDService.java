package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.otus.ModelEnvironment;
import ru.otus.service.commands.RestCommandFactory;
import ru.otus.service.strategy.StrategyFactory;

import java.util.List;

public class RestForCRUDService<E, M, R, Q> implements RestForCRUD<E, M, R, Q> {
    private static final Logger log = LoggerFactory.getLogger(RestForCRUDService.class);
    private final ModelEnvironment<E, M, R, Q> modelEnvironment;
    private final RestCommandFactory<E, M, R, Q> commandFactory;
    private final StrategyFactory<E, M, R, Q> strategyFactory;
    private final ModelProcessor<M> afterModifyProcessor;

    public RestForCRUDService(ModelEnvironment<E, M, R, Q> modelEnvironment,
                              RestCommandFactory<E, M, R, Q> commandFactory,
                              StrategyFactory<E, M, R, Q> strategyFactory,
                              ModelProcessor<M> afterModifyProcessor) {
        this.modelEnvironment = modelEnvironment;
        this.commandFactory = commandFactory;
        this.strategyFactory = strategyFactory;
        this.afterModifyProcessor = afterModifyProcessor;
    }

    @Override
    public List<R> findAll() {
        return modelEnvironment.getRepository()
                .findAll().stream()
                .map(entity -> modelEnvironment.getEntityMapper().toModel(entity))
                .map(model -> modelEnvironment.getResponseMapper().toResponse(model))
                .toList();
    }

    @Override
    public Page<R> findPageable(Pageable pageable) {
        var entities = modelEnvironment.getRepository().findAll(pageable);
        return new PageImpl<>(
                entities.stream()
                        .map(entity -> modelEnvironment.getEntityMapper().toModel(entity))
                        .map(model -> modelEnvironment.getResponseMapper().toResponse(model))
                        .toList(),
                entities.getPageable(),
                entities.getTotalElements());
    }

    @Override
    public R findOne(Long id) {
        return commandFactory.getFindOneCommand()
                .execute(modelEnvironment,
                        model -> model,
                        strategyFactory.getReadStrategy().execute(id, modelEnvironment));
    }

    @Override
    public R create(Q request) {
        return commandFactory.getSaveCommand()
                .execute(modelEnvironment,
                        afterModifyProcessor,
                        strategyFactory.getCreateStrategy().execute(request, modelEnvironment));
    }

    @Override
    public R update(Long id, Q request) {
        return commandFactory.getSaveCommand().execute(modelEnvironment,
                afterModifyProcessor,
                strategyFactory.getUpdateStrategy().execute(id, request, modelEnvironment));
    }


    @Override
    public R delete(Long id) {
        return commandFactory.getDeleteCommand().execute(modelEnvironment,
                afterModifyProcessor,
                strategyFactory.getDeleteStrategy().execute(id, modelEnvironment));
    }

}
