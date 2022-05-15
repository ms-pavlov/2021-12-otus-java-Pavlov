package ru.otus;

@FunctionalInterface
public interface ActionWithTread {
    void execute(CounterThread thread);
}
