package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReflectionMappingStrategyTest {

    @Test
    void toEntityObject() throws SQLException {
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>() {
            @Override
            public Class<Client> getEntityClass() {
                return Client.class;
            }
        };
        var rowset = mock(ResultSet.class);
        when(rowset.getObject("id")).thenReturn(1L);
        when(rowset.getObject("name")).thenReturn("name");
        var strategy = new ReflectionMappingStrategy<>(entityClassMetaDataClient);
        assertEquals(1, strategy.toEntityObject(rowset).getId());
        assertEquals("name", strategy.toEntityObject(rowset).getName());
    }
}