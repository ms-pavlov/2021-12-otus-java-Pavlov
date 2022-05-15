package ru.otus;

public interface ThreadsService {

    void addNewCounterThread(String name);

    void startAll();

    void joinAll();
}
