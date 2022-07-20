package ru.otus.client;

import reactor.core.publisher.Mono;

import java.util.List;

public interface NetService<R, Q> {

    Mono<List<R>> findAll();

    Mono<R> findOne(Long id);

    Mono<R> create(Q request);

    Mono<R> update(Long id, Q request);

}
