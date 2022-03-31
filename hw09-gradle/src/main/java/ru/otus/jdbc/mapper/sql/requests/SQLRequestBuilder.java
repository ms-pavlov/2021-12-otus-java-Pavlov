package ru.otus.jdbc.mapper.sql.requests;

import ru.otus.jdbc.mapper.exception.BadSQLRequestException;
import ru.otus.jdbc.mapper.sql.parameters.SQLParameter;

public interface SQLRequestBuilder {

    String build() throws BadSQLRequestException;

    String buildWithParameters(SQLParameter parameter) throws BadSQLRequestException;
}
