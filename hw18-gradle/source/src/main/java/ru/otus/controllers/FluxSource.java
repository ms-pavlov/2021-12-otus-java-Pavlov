package ru.otus.controllers;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.response.ClientResponseDto;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

public interface FluxSource<T> {
    Flux<List<ClientResponseDto>> getFlux();

    Mono<ClientResponseDto> makeMono(Supplier<ClientResponseDto> supplier, ExecutorService executor);
}
