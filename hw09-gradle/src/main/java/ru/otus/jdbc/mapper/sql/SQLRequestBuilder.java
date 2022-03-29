package ru.otus.jdbc.mapper.sql;

import ru.otus.jdbc.mapper.exception.BadSQLRequestException;

public interface SQLRequestBuilder {

    String build() throws BadSQLRequestException;

    String buildWithParameters(SQLParameter parameter) throws BadSQLRequestException;
}
