package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.sql.SQLParameter;
import ru.otus.jdbc.mapper.sql.SQLRequestsFactory;
import ru.otus.jdbc.mapper.sql.SimpleRequestsFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SimpleCRUDSQLRequest implements EntitySQLMetaData {
    private final SQLRequestsFactory requestsFactory;

    public SimpleCRUDSQLRequest(List<String> fields, String tableName) {
        requestsFactory = new SimpleRequestsFactory(fields, tableName);
    }

    @Override
    public String getSelectAllSql() {
        return requestsFactory.select().build();
    }

    @Override
    public String getSelectByIdSql(SQLParameter id) {
        return requestsFactory.select().buildWithParameters(id);
    }

    @Override
    public String getInsertSql() {
        return requestsFactory.insert().build();
    }

    @Override
    public String getUpdateSql(SQLParameter id) {
        return null;
    }
}
