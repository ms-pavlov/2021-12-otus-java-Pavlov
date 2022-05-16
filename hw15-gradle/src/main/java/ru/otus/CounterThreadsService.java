package ru.otus;

public class CounterThreadsService implements ThreadsService {

    private final ThreadsOrder order;

    public CounterThreadsService(ThreadsOrder order) {
        this.order = order;
    }

    @Override
    public void addNewCounterThread(String name) {
        order.addCounterThread(new TenCounterThread(name, order));
    }

    @Override
    public void startAll() {
        order.forEachThread(CounterThread::startThread);
    }

    @Override
    public void joinAll() {
        order.forEachThread(CounterThread::joinThread);
    }
}
