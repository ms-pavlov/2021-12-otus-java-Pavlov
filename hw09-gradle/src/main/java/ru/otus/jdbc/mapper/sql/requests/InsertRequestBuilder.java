package ru.otus.jdbc.mapper.sql.requests;

import ru.otus.jdbc.mapper.exception.BadSQLRequestException;
import ru.otus.jdbc.mapper.sql.parameters.SQLParameter;

import java.util.List;
import java.util.stream.Collectors;

public class InsertRequestBuilder implements SQLRequestBuilder{
    private static final String OPERATION = "insert into ";
    private static final String SEPARATOR = ", ";
    private static final String VALUES = " values ";
    private static final String SPACE = " ";
    private final List<String> fields;
    private final String tableName;

    public InsertRequestBuilder(List<String> fields, String tableName) {
        this.fields = fields;
        this.tableName = tableName;
    }

    @Override
    public String build() throws BadSQLRequestException {
        return OPERATION
                .concat(buildTable())
                .concat(buildFields())
                .concat(buildValues());
    }

    private String withBracket(String str) {
        return "(".concat(str).concat(")");
    }

    private String buildTable() {
        return tableName.concat(SPACE);
    }

    private String buildFields() {
        return withBracket(String.join(SEPARATOR, fields));
    }

    private String buildValues() {
        return VALUES.concat(withBracket(fields.stream().map(s -> "?").collect(Collectors.joining(SEPARATOR))));
    }

    @Override
    public String buildWithParameters(SQLParameter parameter) throws BadSQLRequestException {
        return build();
    }
}
