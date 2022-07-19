package ru.otus.service.strategy;

public interface StrategyFactory<E, M, R, Q> {
    CreateStrategy<E, M, R, Q> getCreateStrategy();

    UpdateStrategy<E, M, R, Q> getUpdateStrategy();

    DeleteStrategy<E, M, R, Q> getDeleteStrategy();

    ReadStrategy<E, M, R, Q> getReadStrategy();
}
