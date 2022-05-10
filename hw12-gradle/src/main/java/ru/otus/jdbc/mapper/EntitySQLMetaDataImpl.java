package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.sql.*;
import ru.otus.jdbc.mapper.sql.parameters.ParameterOperators;
import ru.otus.jdbc.mapper.sql.parameters.QueryParameter;
import ru.otus.jdbc.mapper.sql.parameters.SQLParameter;
import ru.otus.jdbc.mapper.sql.requests.RequestFields;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final SQLRequestsFactory requestsFactory;
    private final SQLParameter id;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.requestsFactory = new SimpleRequestsFactory(new RequestFields(entityClassMetaData));
        this.id = new QueryParameter(entityClassMetaData.getIdField().getName(), ParameterOperators.EQUAL);
    }

    @Override
    public String getSelectAllSql() {
        return requestsFactory.select().build();
    }

    @Override
    public String getSelectByIdSql() {
        return requestsFactory.select().buildWithParameters(id);
    }

    @Override
    public String getInsertSql() {
        return requestsFactory.insert().build();
    }

    @Override
    public String getUpdateSql() {
        return requestsFactory.update().buildWithParameters(id);
    }
}
