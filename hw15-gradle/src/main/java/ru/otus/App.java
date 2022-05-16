package ru.otus;

public class App {
    public static void main(String... args) {
        ThreadsService threadsService = new CounterThreadsService(new CounterThreadsOrder());
        threadsService.addNewCounterThread("1");
        threadsService.addNewCounterThread("2");

        threadsService.startAll();
        threadsService.joinAll();
    }
}
