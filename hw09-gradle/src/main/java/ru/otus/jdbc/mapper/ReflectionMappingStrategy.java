package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.helpers.ReflectionHelper;
import ru.otus.jdbc.mapper.exception.BadEntityException;
import ru.otus.jdbc.mapper.exception.BadSQLRequestException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReflectionMappingStrategy<T> implements MappingStrategy<T> {
    private final EntityClassMetaData<T> entityClassMetaData;
    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

    public ReflectionMappingStrategy(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public T toEntityObject(ResultSet resultSet) {
        try {
            T result = entityClassMetaData.getConstructor().newInstance();
            entityClassMetaData.getAllFields().stream()
                    .map(Field::getName)
                    .forEach(fieldName -> {
                        ReflectionHelper.setFieldValue(result, fieldName, getValue(resultSet, fieldName));
                    });
            logger.info("mapped: {}", result);
            return result;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BadEntityException(e);
        }
    }

    private Object getValue(ResultSet resultSet, String valueName) {
        try {
            return resultSet.getObject(valueName);
        } catch (SQLException e) {
            throw new BadSQLRequestException(e);
        }
    }
}
