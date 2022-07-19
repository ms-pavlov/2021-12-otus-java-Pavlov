package ru.otus.service.strategy;

public class StrategyFactoryWithSoftDelete<E, M, R, Q> implements StrategyFactory<E, M, R, Q> {
    private final CreateStrategy<E, M, R, Q> createStrategy;
    private final ReadStrategy<E, M, R, Q> readStrategy;
    private final UpdateStrategy<E, M, R, Q> updateStrategy;
    private final DeleteStrategy<E, M, R, Q> deleteStrategy;

    public StrategyFactoryWithSoftDelete(SoftDeleteMarker<M> deleteMarker) {
        this.createStrategy = new SimpleCreateStrategy<>();
        this.readStrategy = new SimpleReadStrategy<>();
        this.updateStrategy = new SimpleUpdateStrategy<>();
        this.deleteStrategy = new SoftDeleteStrategy<>(deleteMarker);
    }

    @Override
    public CreateStrategy<E, M, R, Q> getCreateStrategy() {
        return createStrategy;
    }

    @Override
    public UpdateStrategy<E, M, R, Q> getUpdateStrategy() {
        return updateStrategy;
    }

    @Override
    public DeleteStrategy<E, M, R, Q> getDeleteStrategy() {
        return deleteStrategy;
    }

    @Override
    public ReadStrategy<E, M, R, Q> getReadStrategy() {
        return readStrategy;
    }
}
