package ru.otus.jdbc.mapper;

import ru.otus.helpers.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;

public class ParameterFromEntityForJdbc<T> implements ParameterFromEntity<T> {
    private final EntityClassMetaData<T> entityClassMetaData;

    public ParameterFromEntityForJdbc(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public List<Object> getParameterFromEntityWithId(T object) {
        return addID(new ArrayList<>(getParameterFromEntityWithoutId(object)),
                ReflectionHelper.getFieldValue(object, this.entityClassMetaData.getIdField().getName()));
    }

    @Override
    public List<Object> getParameterFromEntityWithoutId(T object) {
        return entityClassMetaData.getFieldsWithoutId().stream().map(field -> ReflectionHelper.getFieldValue(object, field.getName())).toList();
    }

    @Override
    public List<Object> addID(List<Object> list, Object id) {
        list.add(id);
        return list;
    }

}
