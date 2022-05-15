package ru.otus;

import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

public class CounterThreadsOrder implements ThreadsOrder{
    private final NavigableMap<Integer, CounterThread> threads;
    private Integer next;


    public CounterThreadsOrder() {
        this.threads = new TreeMap<>();
        next = 0;
    }

    @Override
    public boolean isOrder(CounterThread thread) {
        return  threads.get(next).equals(thread);
    }

    @Override
    public void endTurn(CounterThread thread) {
        if (isOrder(thread)) {
            next = Optional.ofNullable(threads.higherKey(next)).orElse(threads.firstKey());
        }
    }

    @Override
    public void addCounterThread(CounterThread thread) {
        threads.put(threads.size(), thread);
    }

    @Override
    public void forEachThread(ActionWithTread action) {
        threads.values().forEach(action::execute);
    }
}
