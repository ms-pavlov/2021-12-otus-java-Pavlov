package ru.otus.jdbc.mapper.sql;

import org.junit.jupiter.api.Test;
import ru.otus.jdbc.mapper.sql.parameters.ParameterOperators;
import ru.otus.jdbc.mapper.sql.parameters.QueryParameter;
import ru.otus.jdbc.mapper.sql.parameters.QueryParameterGroup;

import static org.junit.jupiter.api.Assertions.*;

class QueryParameterGroupTest {

    @Test
    void makeWhere() {
        var params = new QueryParameterGroup(ParameterOperators.AND);
        params.add(new QueryParameter("id", ParameterOperators.LARGER));
        params.add(new QueryParameter("id", ParameterOperators.SMALLER));
        params.add(new QueryParameter("id", ParameterOperators.SMALLER));

        assertEquals("(id > ?) and (id < ?)", params.makeWhere());
    }
}