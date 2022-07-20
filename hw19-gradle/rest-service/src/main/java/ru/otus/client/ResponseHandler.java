package ru.otus.client;

@FunctionalInterface
public interface ResponseHandler<R, M> {
    M execute(R model);
}
