package ru.otus.controllers.interfaces;

import reactor.core.publisher.Mono;
import ru.otus.client.NetService;

public interface CRUDController<R, Q> {

    NetService<R, Q> getService();

    Mono<R> find(Long id);

    Mono<R> create(Q request);

    Mono<R> update(Long id, Q request);
}
