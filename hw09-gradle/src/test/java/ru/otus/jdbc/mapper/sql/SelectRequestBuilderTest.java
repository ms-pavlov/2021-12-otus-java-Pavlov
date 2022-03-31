package ru.otus.jdbc.mapper.sql;

import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.jdbc.mapper.sql.requests.SelectRequestBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class SelectRequestBuilderTest {

    @Test
    void build() {
        var client = new Client();
        var select = new SelectRequestBuilder(Arrays.stream(client.getClass().getDeclaredFields()).map(Field::getName).toList(),
                client.getClass().getSimpleName().toLowerCase(Locale.ROOT));
        var sql = select.build();
        assertEquals("select id, name from client", sql);
    }
}