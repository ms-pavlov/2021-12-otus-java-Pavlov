package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParameterFromEntityForJdbcTest {
    ParameterFromEntityForJdbc<Client> parameters;
    Client testClient;

    @BeforeEach
    void before() {
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        this.parameters = new ParameterFromEntityForJdbc<>(entityClassMetaDataClient);
        this.testClient = new Client(1L, "name");
    }

    @Test
    void getParameterFromEntityWithId() {
        assertEquals(2, parameters.getParameterFromEntityWithId(testClient).size());
        assertEquals(testClient.getId(), parameters.getParameterFromEntityWithId(testClient).get(1));
        assertEquals(testClient.getName(), parameters.getParameterFromEntityWithId(testClient).get(0));
    }

    @Test
    void getParameterFromEntityWithoutId() {
        assertEquals(1, parameters.getParameterFromEntityWithoutId(testClient).size());
        assertEquals(testClient.getName(), parameters.getParameterFromEntityWithoutId(testClient).get(0));
    }

    @Test
    void addID() {
        assertEquals(1, parameters.addID(new ArrayList<>(),testClient).size());
        assertEquals(testClient.getId(), parameters.addID(new ArrayList<>(),testClient.getId()).get(0));
    }
}