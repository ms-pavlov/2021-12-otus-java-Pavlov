package ru.otus.jdbc.mapper.strategy;

import ru.otus.jdbc.mapper.EntityClassMetaData;

@FunctionalInterface
public interface MappingStrategyFactory<T> {
    MappingStrategy<T> getMappingStrategy(EntityClassMetaData<T> entityClassMetaData);
}
