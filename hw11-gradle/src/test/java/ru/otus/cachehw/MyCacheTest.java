package ru.otus.cachehw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MyCacheTest {
    public static String KEY = "key";
    public static String VALUE = "value";
    public static String MESSAGES_PROPERTY = "messages";
    private MyCache<Object, String> cache;
    private List<String> messages;
    private int count;

    public static String getAction(String actionName) {
        return ResourceBundle.getBundle(MESSAGES_PROPERTY).getString(actionName);
    }

    @BeforeEach
    void beforeEach() {
        cache = new MyCache<>();
        count = 5;
        messages = new ArrayList<>();
        for (var i = 0; i < count; i++) {
            int iterator = i;
            cache.addListener((key, value, action) -> messages.add(iterator + key.toString() + value + action));
        }
    }

    @Test
    void put() {
        cache.put(KEY, VALUE);
        assertEquals(count, messages.size());
        messages.forEach(message -> assertEquals(messages.indexOf(message) + KEY + VALUE + getAction("put"), message));
    }

    @Test
    void remove() {
        cache.put(KEY, VALUE);
        messages.clear();
        cache.remove(KEY);
        assertEquals(count, messages.size());
        messages.forEach(message -> assertEquals(messages.indexOf(message) + KEY + VALUE + getAction("remove"), message));
    }

    @Test
    void get() {
        cache.put(KEY, VALUE);
        messages.clear();
        var result = cache.get(KEY);

        assertEquals(count, messages.size());
        messages.forEach(message -> assertEquals(messages.indexOf(message) + KEY + VALUE + getAction("get"), message));
        assertEquals(VALUE, result);

        System.gc();
        assertNull(cache.get(KEY));
    }
}