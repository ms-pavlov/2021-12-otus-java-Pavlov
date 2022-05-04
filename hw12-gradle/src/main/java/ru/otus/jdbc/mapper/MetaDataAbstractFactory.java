package ru.otus.jdbc.mapper;

public interface MetaDataAbstractFactory<T>{

    EntitySQLMetaData getEntitySQLMetaData();
    Mapper<T> getMapper();
    ParameterFromEntity<T> getParameters();
    EntityForJdbc<T> getEntityForJdbc();
}
