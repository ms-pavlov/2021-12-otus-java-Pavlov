package ru.otus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CounterThreadsServiceTest {
    private final static String THREAD1_NAME = "1";
    private final static String THREAD2_NAME = "2";

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
    void integrationWithCounterThreadsOrder() {
        ThreadsService threadsService = new CounterThreadsService(new CounterThreadsOrder());
        threadsService.addNewCounterThread(THREAD1_NAME);
        threadsService.addNewCounterThread(THREAD2_NAME);

        threadsService.startAll();
        threadsService.joinAll();
        assertEquals(getExpectedString(), outContent.toString());
    }

    private String getExpectedString() {
        var expected = new ByteArrayOutputStream();
        var writer = new PrintStream(expected);
        for (var i =1; i<=10; i++) {
            writer.println(prepareMessage(THREAD1_NAME, i));
            writer.println(prepareMessage(THREAD2_NAME, i));
        }
        for (var i =9; i>0; i--) {
            writer.println(prepareMessage(THREAD1_NAME, i));
            writer.println(prepareMessage(THREAD2_NAME, i));
        }
        return expected.toString();
    }

    private String prepareMessage(String threadName, int i) {
        return "Thread ".concat(threadName).concat(": ").concat(String.valueOf(i));
    }
}