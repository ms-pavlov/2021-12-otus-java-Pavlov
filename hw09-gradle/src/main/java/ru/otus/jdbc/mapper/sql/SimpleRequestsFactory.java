package ru.otus.jdbc.mapper.sql;

import java.util.List;

public class SimpleRequestsFactory implements SQLRequestsFactory {
    private final List<String> fields;
    private final String tableName;

    public SimpleRequestsFactory(List<String> fields, String tableName) {
        this.fields = fields;
        this.tableName = tableName;
    }

    @Override
    public SQLRequestBuilder select() {
        return new SelectRequestBuilder(fields, tableName);
    }

    @Override
    public SQLRequestBuilder update() {
        return new UpdateRequestBuilder(fields, tableName);
    }

    @Override
    public SQLRequestBuilder insert() {
        return new InsertRequestBuilder(fields, tableName);
    }

    @Override
    public SQLRequestBuilder delete() {
        return null;
    }
}
