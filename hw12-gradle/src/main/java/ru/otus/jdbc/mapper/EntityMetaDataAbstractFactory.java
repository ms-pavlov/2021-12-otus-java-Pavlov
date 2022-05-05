package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.strategy.MappingStrategyFactory;

public class EntityMetaDataAbstractFactory<T> implements MetaDataAbstractFactory<T> {
    private final EntitySQLMetaData entitySQLMetaData;
    private final Mapper<T> mapper;
    private final ParameterFromEntity<T> parameters;
    private final EntityForJdbc<T> entity;

    public EntityMetaDataAbstractFactory(Class<T> entityClass, MappingStrategyFactory<T> mappingStrategyFactory) {
        var entityClassMetaData = new EntityClassMetaDataImpl<>(entityClass);
        this.entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        this.parameters = new ParameterFromEntityForJdbc<>(entityClassMetaData);
        this.mapper = new Mapper<>(mappingStrategyFactory.getMappingStrategy(entityClassMetaData));
        this.entity = new EntityForJdbcImpl<>(entityClassMetaData);
    }

    @Override
    public EntitySQLMetaData getEntitySQLMetaData() {
        return entitySQLMetaData;
    }

    @Override
    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public ParameterFromEntity<T> getParameters() {
        return parameters;
    }

    @Override
    public EntityForJdbc<T> getEntityForJdbc() {
        return entity;
    }
}
