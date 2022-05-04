package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.mapper.strategy.ReflectionMappingStrategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MapperTest {
    Mapper<Client> mapper;

    @BeforeEach
    void before () {
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        var strategy = new ReflectionMappingStrategy<>(entityClassMetaDataClient);
        this.mapper = new Mapper<>(strategy);
    }

    @Test
    void makeEntity() throws SQLException {
        var rowSet = mock(ResultSet.class);
        when(rowSet.getObject("id")).thenReturn(1L);
        when(rowSet.getObject("name")).thenReturn("name");
        when(rowSet.next()).thenReturn(true).thenReturn(false);
        Client result = mapper.makeEntity(rowSet);
        assertEquals(1, result.getId());
        assertEquals("name", result.getName());
    }

    @Test
    void makeEntityList() throws SQLException {
        var rowSet1 = mock(ResultSet.class);
        when(rowSet1.getObject("id")).thenReturn(1L);
        when(rowSet1.getObject("name")).thenReturn("name");
        when(rowSet1.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Client> result = mapper.makeEntityList(rowSet1);
        assertEquals(2, result.size());
        for (var i = 0; i<2; i++) {
            assertEquals(1, result.get(i).getId());
            assertEquals("name", result.get(i).getName());
        }
    }
}