package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.jdbc.mapper.sql.ParameterOperators;
import ru.otus.jdbc.mapper.sql.QueryParameter;
import ru.otus.jdbc.mapper.sql.SQLRequestsFactory;
import ru.otus.jdbc.mapper.sql.SimpleRequestsFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCRUDSQLRequestTest {

    EntitySQLMetaData crud;

    @BeforeEach
    void before() {
        var fields = Arrays.stream(Client.class.getDeclaredFields()).map(Field::getName).toList();
        var tableName = Client.class.getSimpleName().toLowerCase(Locale.ROOT);
        crud = new SimpleCRUDSQLRequest(fields, tableName);
    }

    @Test
    void getSelectAllSql() {
        assertEquals("select id, name from client", crud.getSelectAllSql());
    }

    @Test
    void getSelectByIdSql() {
        var param = new QueryParameter("id", ParameterOperators.EQUAL);
        assertEquals("select id, name from client where id = ?", crud.getSelectByIdSql(param));
    }

    @Test
    void getInsertSql() {
        assertEquals("insert into client (id, name) values (?, ?)", crud.getInsertSql());
    }

    @Test
    void getUpdateSql() {
        var param = new QueryParameter("id", ParameterOperators.EQUAL);
        assertEquals("update client set name = ? where id = ?", crud.getUpdateSql(param));
    }
}