package ru.otus.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NetService<R, Q> {

    Flux<R> findAll();

    Mono<R> create(Q request);

    Mono<R> update(Long id, Q request);

}
