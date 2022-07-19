package ru.otus.rest;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

public class SimpleMonoMaker<R> implements MonoMaker<R> {
    @Override
    public Mono<R> makeMono(Supplier<R> supplier, ExecutorService executor) {
        return Mono.fromFuture(CompletableFuture
                .supplyAsync(supplier, executor));
    }

    @Override
    public Mono<List<R>> makeMonoList(Supplier<List<R>> supplier, ExecutorService executor) {
        return Mono.fromFuture(CompletableFuture
                .supplyAsync(supplier, executor));
    }

    @Override
    public Mono<Page<R>> makeMonoPage(Supplier<Page<R>> supplier, ExecutorService executor) {
        return Mono.fromFuture(CompletableFuture
                .supplyAsync(supplier, executor));
    }


}
