package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.mapper.sql.parameters.ParameterOperators;
import ru.otus.jdbc.mapper.sql.parameters.QueryParameter;

import static org.junit.jupiter.api.Assertions.*;

class EntitySQLMetaDataImplTest {

    EntitySQLMetaData crud;

    @BeforeEach
    void before() {
        var entityClassMetaData = new EntityClassMetaDataImpl<>(Client.class);
        crud = new EntitySQLMetaDataImpl(entityClassMetaData);
    }

    @Test
    void getSelectAllSql() {
        assertEquals("select id, name from client", crud.getSelectAllSql());
    }

    @Test
    void getSelectByIdSql() {
        var param = new QueryParameter("id", ParameterOperators.EQUAL);
        assertEquals("select id, name from client where id = ?", crud.getSelectByIdSql());
    }

    @Test
    void getInsertSql() {
        assertEquals("insert into client (name) values (?)", crud.getInsertSql());
    }

    @Test
    void getUpdateSql() {
        var param = new QueryParameter("id", ParameterOperators.EQUAL);
        assertEquals("update client set name = ? where id = ?", crud.getUpdateSql());
    }
}