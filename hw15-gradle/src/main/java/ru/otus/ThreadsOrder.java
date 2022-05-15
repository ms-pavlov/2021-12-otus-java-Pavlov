package ru.otus;

public interface ThreadsOrder {

    boolean isOrder(CounterThread thread);

    void endTurn(CounterThread thread);

    void addCounterThread(CounterThread thread);

    void forEachThread(ActionWithTread action);

}
