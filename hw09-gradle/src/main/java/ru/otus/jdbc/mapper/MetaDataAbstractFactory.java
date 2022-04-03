package ru.otus.jdbc.mapper;

import ru.otus.core.repository.executor.DbExecutor;

public interface MetaDataAbstractFactory<T>{

    EntitySQLMetaData getEntitySQLMetaData();
    Mapper<T> getMapper();
    ParameterFromEntity<T> getParameters();
}
