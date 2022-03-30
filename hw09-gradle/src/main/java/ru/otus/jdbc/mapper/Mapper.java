package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.helpers.ReflectionHelper;
import ru.otus.jdbc.mapper.exception.BadEntityException;
import ru.otus.jdbc.mapper.exception.BadSQLRequestException;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper<T> {
    private final EntityClassMetaData<T> entityClassMetaData;
    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

    public Mapper(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    public T makeEntity(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                T result = entityClassMetaData.getConstructor().newInstance();
                entityClassMetaData.getAllFields()
                        .forEach(field -> {
                            try {
                                ReflectionHelper.setFieldValue(result, field.getName(), resultSet.getObject(field.getName()));
                            } catch (SQLException e) {
                                throw new BadSQLRequestException(e);
                            }
                        });
                logger.info("mapped: {}", result);
                return result;
            }
            return null;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BadEntityException(e);
        } catch (SQLException e) {
            throw new BadSQLRequestException(e);
        }
    }
}
