package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.services.JdbcService;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


public class ClientSource implements FluxSource<ClientResponseDto> {
    private static final Logger log = LoggerFactory.getLogger(ClientSource.class);
    private static final int ELEMENT_DELAY = 1;
    private static final int GENERATOR_PERIOD = 1;
    private static final int INITIAL_DELAY = 0;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private final Flux<List<ClientResponseDto>> flux;
    private final JdbcService<ClientResponseDto, ClientRequestDto> clientService;
    private final Scheduler timer;

    public ClientSource(JdbcService<ClientResponseDto, ClientRequestDto> clientService, Scheduler timer) {
        this.clientService = clientService;
        this.timer = timer;
        this.flux = Flux.create(this::generatePeriodically)
                .delayElements(Duration.ofSeconds(ELEMENT_DELAY), timer)
                .doOnNext(val -> log.info("val:{}", val));

    }

    private void generatePeriodically(FluxSink<List<ClientResponseDto>> listFluxSink) {
        timer.schedulePeriodically(() -> listFluxSink.next(clientService.findAll()),
                INITIAL_DELAY, GENERATOR_PERIOD, TIME_UNIT);
    }

    @Override
    public Flux<List<ClientResponseDto>> getFlux() {
        return flux;
    }

    @Override
    public Mono<ClientResponseDto> makeMono(Supplier<ClientResponseDto> supplier, ExecutorService executor) {
        return Mono.fromFuture(CompletableFuture
                .supplyAsync(supplier, executor));
    }
}
