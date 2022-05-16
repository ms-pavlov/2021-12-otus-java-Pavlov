package ru.otus;

public interface CounterThread extends Runnable{

    void startThread();

    void joinThread();
}
