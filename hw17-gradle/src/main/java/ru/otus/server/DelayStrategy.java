package ru.otus.server;

@FunctionalInterface
public interface DelayStrategy {

    long getDelay();
}
