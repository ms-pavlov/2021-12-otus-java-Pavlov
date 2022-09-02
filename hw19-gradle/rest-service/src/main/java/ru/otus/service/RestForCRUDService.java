package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.ModelEnvironment;
import ru.otus.aspects.ArgsLog;
import ru.otus.mappers.ResponseMapper;
import ru.otus.service.repositories.CRUDModel;
import ru.otus.service.strategy.RequestStrategy;
import ru.otus.service.strategy.SoftDeleteMarker;

import java.util.List;

public class RestForCRUDService< M, R, Q> implements RestForCRUD<M, Q> {
    private static final Logger log = LoggerFactory.getLogger(RestForCRUDService.class);
    private final ModelProcessor<M> afterModifyProcessor;
    private final RequestStrategy<M, Q> requestStrategy;
    private final CRUDModel<M> dataSource;
    private final SoftDeleteMarker<M> deleteMarker;

    public RestForCRUDService(ModelEnvironment<M, Q> modelEnvironment) {
        this.afterModifyProcessor = modelEnvironment.getAfterModifyProcessor();
        this.requestStrategy = modelEnvironment.getRequestStrategy();
        this.dataSource = modelEnvironment.getDataSource();
        this.deleteMarker = modelEnvironment.getDeleteMarker();
    }

    @Override
    public List<M> findAll() {
        return dataSource.findAll().stream()
                .toList();
    }

    @Override
    public Page<M> findPageable(Pageable pageable) {
        return dataSource.findPageable(pageable);
    }

    @Override
    public M findOne(Long id) {
        return dataSource.findOne(id)
                .orElse(null);
    }

    @Override
    @ArgsLog
    public M create(Q request) {
        return requestStrategy
                .execute(request)
                .map(this::saveModel)
                .orElse(null);
    }

    @Override
    @ArgsLog
    public M update(Long id, Q request) {
        return requestStrategy
                .execute(dataSource.findOne(id).orElse(null), request)
                .map(this::saveModel)
                .orElse(null);
    }

    @Override
    @ArgsLog
    public M delete(Long id) {
        return dataSource.findOne(id)
                .map(deleteMarker::markDeleted)
                .map(this::saveModel)
                .orElse(null);
    }

    private M saveModel(M model) {
        return afterModifyProcessor.process(dataSource.save(model));
    }

}
