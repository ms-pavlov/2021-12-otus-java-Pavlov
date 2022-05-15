package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenCounterThread extends Thread implements CounterThread {
    private static final Logger log = LoggerFactory.getLogger(TenCounterThread.class);
    private static final int MAX_COUNT = 10;
    private final ThreadsOrder order;
    private int counter;
    private int increment;

    public TenCounterThread(String name, ThreadsOrder order) {
        this.counter = 1;
        this.increment = 1;
        this.order = order;
        this.setName(name);
    }

    @Override
    public void run() {
        synchronized (order) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    waitThisThreadTurn();
                    printNumber();
                    endTurn();
                    getNextNumber();
                    checkNumber();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void waitThisThreadTurn() throws InterruptedException {
        while (!order.isOrder(this)) {
            order.wait();
        }
    }

    public void printNumber() {
        System.out.println("Thread ".concat(getName()).concat(": ").concat(String.valueOf(counter)));
    }

    private void getNextNumber() {
        if (MAX_COUNT == counter) {
            increment = -1;
        }
        counter += increment;
    }

    private void checkNumber() {
        if (1 > counter) {
            Thread.currentThread().interrupt();
        }
    }

    private void endTurn() {
        order.endTurn(this);
        order.notifyAll();
    }

    @Override
    public void startThread() {
        this.start();
    }

    @Override
    public void joinThread() {
        try {
            this.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
