package ru.otus.jdbc.mapper.sql.requests;

import ru.otus.jdbc.mapper.exception.BadSQLRequestException;
import ru.otus.jdbc.mapper.sql.parameters.SQLParameter;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateRequestBuilder implements SQLRequestBuilder {
    private static final String OPERATION = "update ";
    private static final String SEPARATOR = ", ";
    private static final String SET = " set ";
    private static final String SPACE = " ";
    private static final String WHERE = " where ";

    private final List<String> fields;
    private final String tableName;

    public UpdateRequestBuilder(List<String> fields, String tableName) {
        this.fields = fields;
        this.tableName = tableName;
    }

    @Override
    public String build() throws BadSQLRequestException {
        return OPERATION
                .concat(buildTable())
                .concat(buildFields());
    }

    @Override
    public String buildWithParameters(SQLParameter parameter) throws BadSQLRequestException {
        return build().concat(WHERE).concat(parameter.makeWhere());
    }

    private String buildTable() {
        return tableName;
    }
    private String buildFields() {
        return SET. concat(fields.stream().map(s -> s.concat(" = ?")).collect(Collectors.joining(SEPARATOR)));
    }
}
