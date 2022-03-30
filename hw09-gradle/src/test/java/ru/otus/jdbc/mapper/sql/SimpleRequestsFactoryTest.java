package ru.otus.jdbc.mapper.sql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRequestsFactoryTest {

    SQLRequestsFactory srf;

    @BeforeEach
    void before() {
        var requestFields = new RequestFields(new EntityClassMetaDataImpl<Client>() {
            @Override
            public Class<Client> getEntityClass() {
                return Client.class;
            }
        });
        srf = new SimpleRequestsFactory(requestFields);
    }

    @Test
    void select() {
        assertEquals("select id, name from client", srf.select().build());
    }

    @Test
    void update() {
        var param = new QueryParameter("id", ParameterOperators.EQUAL);
        assertEquals("update client set name = ? where id = ?", srf.update().buildWithParameters(param));
    }

    @Test
    void insert() {
        assertEquals("insert into client (name) values (?)", srf.insert().build());
    }

}