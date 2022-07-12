package ru.otus.services;

@FunctionalInterface
public interface ResponseHandler<R, M> {
    M execute(R model);
}
