package ru.otus.cachehw.messages;

import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class PutMessagesTest {
    public static String MESSAGES_PROPERTY = "messages";
    @Test
    void getAction() {
        var msg = new PutMessages<>(null, null);
        assertEquals(ResourceBundle.getBundle(MESSAGES_PROPERTY).getString("put"),msg.getAction());
    }
}