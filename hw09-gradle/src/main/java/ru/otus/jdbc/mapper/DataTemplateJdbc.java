package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.helpers.ReflectionHelper;
import ru.otus.jdbc.mapper.exception.BadSQLRequestException;

import java.sql.Connection;
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
    private final Mapper<T> mapper;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
        this.mapper = new Mapper<>(entityClassMetaData);
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        List<Object> params = new ArrayList<>();
        params.add(id);
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), params, mapper::makeEntity);
    }

    @Override
    public List<T> findAll(Connection connection) {
        try (var pst = connection.prepareStatement(entitySQLMetaData.getSelectAllSql())) {
            try (var rs = pst.executeQuery()) {
                List<T> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapper.makeEntity(rs));
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
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getParameterFromEntityWithoutId(client));
    }

    @Override
    public void update(Connection connection, T client) {
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), getParameterFromEntityWithId(client));
    }

    private List<Object> getParameterFromEntityWithId(T client) {
        var params = getParameterFromEntityWithoutId(client);
        params.add(ReflectionHelper.getFieldValue(client, this.entityClassMetaData.getIdField().getName()));
        return params;
    }

    private List<Object> getParameterFromEntityWithoutId(T client) {
        return entityClassMetaData.getFieldsWithoutId().stream().map(field -> ReflectionHelper.getFieldValue(client, field.getName())).toList();
    }
}
