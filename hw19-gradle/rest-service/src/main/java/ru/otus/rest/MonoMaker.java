package ru.otus.rest;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

public interface MonoMaker<R> {
    Mono<R> makeMono(Supplier<R> supplier, ExecutorService executor);

    Mono<List<R>> makeMonoList(Supplier<List<R>> supplier, ExecutorService executor);

    Mono<Page<R>> makeMonoPage(Supplier<Page<R>> supplier, ExecutorService executor);
}
