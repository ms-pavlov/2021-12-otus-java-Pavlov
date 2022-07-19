package ru.otus.rest;

import reactor.core.publisher.Flux;

public interface FluxPublisher<M> {
    void send(M message);
    Flux<M> getFlux();
}
