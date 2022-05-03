package ru.otus.cachehw.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractMessagesTest {
    private CacheMessage<String, String> msg;
    public static String KEY ="key";
    public static String VALUE ="value";

    @BeforeEach
    void beforeAll() {
        msg = new AbstractMessages<>(KEY, VALUE) {
            @Override
            public String getAction() {
                return null;
            }
        };
    }

    @Test
    void getKey() {
        assertEquals(KEY, msg.getKey());
    }

    @Test
    void getValue() {
        assertEquals(VALUE, msg.getValue());
    }
}