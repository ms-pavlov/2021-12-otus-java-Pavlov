package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.jdbc.crm.model.Client;

import static org.junit.jupiter.api.Assertions.*;

class EntityForJdbcImplTest {
    private EntityForJdbc<Client> entityForJdbc;

    @BeforeEach
    void before() {
        var entityClassMetaData = new EntityClassMetaDataImpl<>(Client.class);
        this.entityForJdbc = new EntityForJdbcImpl<>(entityClassMetaData);
    }


    @Test
    void isIDNull() {
        var client = new Client("Тест");
        assertTrue(entityForJdbc.isIDNull(client));

        client.setId(10L);

        assertFalse(entityForJdbc.isIDNull(client));

    }

    @Test
    void setID() {
        var client = new Client("Тест");
        assertTrue(entityForJdbc.isIDNull(client));
        entityForJdbc.setID(10L, client);

        assertEquals(10L, client.getId());
    }
}