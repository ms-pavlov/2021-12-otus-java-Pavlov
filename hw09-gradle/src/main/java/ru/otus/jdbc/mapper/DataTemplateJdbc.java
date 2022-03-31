package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.jdbc.mapper.strategy.MappingStrategyFactory;

import java.sql.Connection;
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
    private final Mapper<T> mapper;
    private final ParameterFromEntity<T> parameters;

    public DataTemplateJdbc(DbExecutor dbExecutor, MetaDataAbstractFactory<T> metaDataAbstractFactory) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = metaDataAbstractFactory.getEntitySQLMetaData();
        this.parameters = metaDataAbstractFactory.getParameters();
        this.mapper = metaDataAbstractFactory.getMapper();
    }



    @Override
    public Optional<T> findById(Connection connection, long id) {
        logger.info("findById sql {}", entitySQLMetaData.getSelectByIdSql());
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), parameters.addID(new ArrayList<>(), id), mapper::makeEntity);
    }

    @Override
    public List<T> findAll(Connection connection) {
        logger.info("findAll sql {}", entitySQLMetaData.getSelectByIdSql());
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), new ArrayList<>(), mapper::makeEntityList)
                .orElse(new ArrayList<>());
    }

    @Override
    public long insert(Connection connection, T client) {
        logger.info("insert sql {}", entitySQLMetaData.getSelectByIdSql());
        var result = dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), parameters.getParameterFromEntityWithoutId(client));
        logger.info("inserted: {}", result);
        return result;
    }

    @Override
    public void update(Connection connection, T client) {
        logger.info("update sql {}", entitySQLMetaData.getSelectByIdSql());
        var result = dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), parameters.getParameterFromEntityWithId(client));
        logger.info("updated: {}", result);
    }
}
