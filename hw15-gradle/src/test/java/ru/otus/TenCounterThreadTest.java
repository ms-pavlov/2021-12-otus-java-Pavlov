package ru.otus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TenCounterThreadTest {
    private static final String THREAD_NAME = "1";

    private ByteArrayOutputStream outContent;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void run() throws InterruptedException {
        var order = mock(ThreadsOrder.class);

        var thread = spy(new TenCounterThread(THREAD_NAME, order));
        when(order.isOrder(thread)).thenReturn(true);
        thread.start();
        thread.join();

        assertEquals(getExpectedString(thread.getName()), outContent.toString());
    }

    private String getExpectedString(String threadName) {
        var expected = new ByteArrayOutputStream();
        var writer = new PrintStream(expected);
        for (var i =1; i<=10; i++) {
            writer.println(prepareMessage(threadName, i));
        }
        for (var i =9; i>0; i--) {
            writer.println(prepareMessage(threadName, i));
        }
        return expected.toString();
    }

    private String prepareMessage(String threadName, int i) {
        return "Thread ".concat(threadName).concat(": ").concat(String.valueOf(i));
    }
}