package ru.otus.jdbc.mapper.sql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRequestsFactoryTest {

    SQLRequestsFactory srf;

    @BeforeEach
    void before() {
        var fields = Arrays.stream(Client.class.getDeclaredFields()).map(Field::getName).toList();
        var tableName = Client.class.getSimpleName().toLowerCase(Locale.ROOT);
        srf = new SimpleRequestsFactory(fields, tableName);
    }

    @Test
    void select() {
        assertEquals("select id, name from client", srf.select().build());
    }

    @Test
    void update() {
        var param = new QueryParameter("id", ParameterOperators.EQUAL);
        assertEquals("update client set id = ?, name = ? where id = ?", srf.update().buildWithParameters(param));
    }

    @Test
    void insert() {
        assertEquals("insert into client (id, name) values (?, ?)", srf.insert().build());
    }

    @Test
    void delete() {
    }
}