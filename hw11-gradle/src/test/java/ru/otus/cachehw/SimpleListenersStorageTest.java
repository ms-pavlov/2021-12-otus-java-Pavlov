package ru.otus.cachehw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.cachehw.messages.PutMessages;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleListenersStorageTest {
    private SimpleListenersStorage<String, String> storage;

    @BeforeEach
    void beforeEach() {
        storage = new SimpleListenersStorage<>();
    }
    @AfterEach
    void afterEach() {
        storage.clean();
    }


    @Test
    void add() {
        int count = 5;
        final List<String> messages = new ArrayList<>();
        for (var i = 0; i < count; i++) {
            int iterator = i;
            storage.add((key, value, action) -> messages.add(iterator + " in list"));
        }
        storage.notifyAll(new PutMessages<>("", ""));
        assertEquals(count, messages.size());
        messages.forEach(message -> assertEquals(messages.indexOf(message) + " in list", message));
    }

    @Test
    void remove() {
        List<String> messages = new ArrayList<>();
        HwListener<String, String> listener = (key, value, action) -> messages.add("in list");
        storage.add(listener);
        storage.notifyAll(new PutMessages<>("", ""));
        messages.forEach(message -> assertEquals("in list", message));
        assertEquals(1, messages.size());
        messages.clear();

        storage.remove(listener);
        storage.notifyAll(new PutMessages<>("", ""));
        assertEquals(0, messages.size());
    }
}