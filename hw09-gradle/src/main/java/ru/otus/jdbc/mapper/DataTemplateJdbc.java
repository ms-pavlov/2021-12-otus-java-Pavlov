package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.jdbc.mapper.exception.BadEntityException;
import ru.otus.jdbc.mapper.exception.BadSQLRequestException;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private static final Logger logger = LoggerFactory.getLogger(DataTemplateJdbc.class);
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        try (var pst = connection.prepareStatement(entitySQLMetaData.getSelectByIdSql())) {
            pst.setLong(1, id);
            try (var rs = pst.executeQuery()) {
                T result = null;
                if (rs.next()) {
                    result = makeEntity(rs);
                }
                logger.info("findById: {}", result);
                return Optional.ofNullable(result);
            } catch (SQLException e) {
                throw new BadSQLRequestException(e);
            }
        } catch (SQLException e) {
            throw new BadSQLRequestException(e);
        }
    }

    @Override
    public List<T> findAll(Connection connection) {
        try (var pst = connection.prepareStatement(entitySQLMetaData.getSelectAllSql())) {
            try (var rs = pst.executeQuery()) {
                List<T> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(makeEntity(rs));
                }
                logger.info("find: {}", result);
                return result;
            } catch (SQLException e) {
                throw new BadSQLRequestException(e);
            }
        } catch (SQLException e) {
            throw new BadSQLRequestException(e);
        }
    }

    @Override
    public long insert(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }

    private T makeEntity(ResultSet resultSet) {
        try {
            var args = entityClassMetaData.getAllFields().stream().map(field -> {
                try {
                    return resultSet.getObject(field.getName());
                } catch (SQLException e) {
                    throw new BadSQLRequestException(e);
                }
            }).toArray();
            return entityClassMetaData.getConstructor().newInstance(args);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BadEntityException(e);
        }
    }
}
