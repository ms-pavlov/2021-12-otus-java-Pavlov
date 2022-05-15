package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CounterThreadsOrderTest {

    private CounterThreadsOrder order;
    private TenCounterThread thread1;
    private TenCounterThread thread2;

    @BeforeEach
    void setUp() {
        order = new CounterThreadsOrder();
        thread1 = new TenCounterThread("1", order);
        thread2 = new TenCounterThread("2", order);

        order.addCounterThread(thread1);
        order.addCounterThread(thread2);
    }

    @Test
    void isOrder() {
        assertTrue(order.isOrder(thread1));
        assertFalse(order.isOrder(thread2));
    }

    @Test
    void endTurn() {
        assertTrue(order.isOrder(thread1));

        order.endTurn(thread2);
        assertTrue(order.isOrder(thread1));
        assertFalse(order.isOrder(thread2));

        order.endTurn(thread1);
        assertFalse(order.isOrder(thread1));
        assertTrue(order.isOrder(thread2));

        order.endTurn(thread2);
        assertTrue(order.isOrder(thread1));
        assertFalse(order.isOrder(thread2));
    }
}