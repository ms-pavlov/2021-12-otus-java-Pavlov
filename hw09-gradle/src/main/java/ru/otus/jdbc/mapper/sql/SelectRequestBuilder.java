package ru.otus.jdbc.mapper.sql;

import ru.otus.jdbc.mapper.exception.BadSQLRequestException;

import java.util.List;

public class SelectRequestBuilder implements SQLRequestBuilder {
    private static final String OPERATION = "select ";
    private static final String FROM = " from ";
    private static final String ALL = " * ";
    private static final String SEPARATOR = ", ";
    private static final String WHERE = " where ";

    private final List<String> fields;
    private final String tableName;

    public SelectRequestBuilder(List<String> fields, String tableName) {
        this.fields = fields;
        this.tableName = tableName;
    }

    @Override
    public String build() throws BadSQLRequestException {
        return OPERATION
                .concat(buildFields())
                .concat(buildTable());
    }

    @Override
    public String buildWithParameters(SQLParameter parameter) throws BadSQLRequestException {
        return build().concat(WHERE).concat(parameter.makeWhere());
    }

    private String buildFields() {
        return fields.isEmpty() ? ALL : String.join(SEPARATOR, fields);
    }

    private String buildTable() {
        return FROM.concat(tableName);
    }

}
