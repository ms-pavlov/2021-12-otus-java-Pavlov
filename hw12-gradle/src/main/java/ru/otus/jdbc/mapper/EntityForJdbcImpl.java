package ru.otus.jdbc.mapper;

import ru.otus.helpers.ReflectionHelper;

public class EntityForJdbcImpl<E> implements EntityForJdbc<E>{
    private final EntityClassMetaData<E> entityClassMetaData;

    public EntityForJdbcImpl(EntityClassMetaData<E> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public boolean isIDNull(E entity) {
        return null == ReflectionHelper.getFieldValue(entity, this.entityClassMetaData.getIdField().getName());
    }

    @Override
    public E setID(long id, E entity) {
        ReflectionHelper.setFieldValue(entity, this.entityClassMetaData.getIdField().getName(), id);
        return entity;
    }
}
