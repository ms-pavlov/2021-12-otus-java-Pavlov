package ru.otus.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.helpers.PropertiesHelper.*;

class PropertiesHelperTest {

    @Test
    void getProperty() {
        Assertions.assertEquals("messages", PropertiesHelper.getProperty(CONFIG, ERROR));
    }

    @Test
    void getErrorConfig() {
        Assertions.assertEquals("messages", PropertiesHelper.getErrorConfig());
    }

    @Test
    void getTestConfig() {
        Assertions.assertEquals("test", PropertiesHelper.getTestConfig());
    }

    @Test
    void errorMessage() {
        Assertions.assertEquals("testError", PropertiesHelper.errorMessage("testError"));
    }

    @Test
    void testMessage() {
        Assertions.assertEquals("test", PropertiesHelper.testMessage("test"));
    }
}