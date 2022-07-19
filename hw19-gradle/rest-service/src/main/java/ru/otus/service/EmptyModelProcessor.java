package ru.otus.service;

public class EmptyModelProcessor<M> implements ModelProcessor<M> {
    @Override
    public M process(M model) {
        return model;
    }
}
