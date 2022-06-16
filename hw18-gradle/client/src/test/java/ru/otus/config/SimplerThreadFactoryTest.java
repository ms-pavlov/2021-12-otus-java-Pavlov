package ru.otus.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimplerThreadFactoryTest {
    private final static String THREAD_NAME_PREFIX = "prefix-";

    @Test
    void newThread() throws InterruptedException {
        var factory = new SimplerThreadFactory(THREAD_NAME_PREFIX);
        final String[] value = {"False"};
        var thread = factory.newThread(() -> value[0] = "Ok");

        assertNotNull(thread);

        thread.start();
        thread.join();

        assertEquals("Ok", value[0]);
        assertEquals(THREAD_NAME_PREFIX+1, thread.getName());
    }
}