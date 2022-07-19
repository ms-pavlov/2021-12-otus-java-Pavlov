package ru.otus.service.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestCommandFactoryImplTest {

    private RestCommandFactory<Object, Object, Object, Object> commandFactory;

    @BeforeEach
    void setUp() {
        commandFactory = new RestCommandFactoryImpl<>();
    }

    @Test
    void getFindOneCommand() {
        assertNotNull(commandFactory.getFindOneCommand());
        assertEquals(FindServiceCommand.class, commandFactory.getFindOneCommand().getClass());
    }

    @Test
    void getSaveCommand() {
        assertNotNull(commandFactory.getSaveCommand());
        assertEquals(SaveServiceCommand.class, commandFactory.getSaveCommand().getClass());
    }

    @Test
    void getDeleteCommand() {
        assertNotNull(commandFactory.getDeleteCommand());
        assertEquals(SoftDeleteServiceCommand.class, commandFactory.getDeleteCommand().getClass());
    }
}