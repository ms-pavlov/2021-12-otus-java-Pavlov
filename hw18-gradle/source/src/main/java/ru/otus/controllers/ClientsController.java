package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Scheduler;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.services.ClientService;
import ru.otus.services.JdbcService;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;

@RestController
@RequestMapping(value = "/api/clients/",
        produces = MediaType.APPLICATION_NDJSON_VALUE)
public class ClientsController {
    private static final Logger log = LoggerFactory.getLogger(ClientsController.class);
    private final JdbcService<ClientResponseDto, ClientRequestDto> clientService;
    private final ExecutorService executor;
    private final Scheduler timer;

    public ClientsController(ClientService clientService, ExecutorService executor, Scheduler timer) {
        this.clientService = clientService;
        this.executor = executor;
        this.timer = timer;
    }

    @GetMapping(value = "/all/")
    public Flux<List<ClientResponseDto>> findAll() {
//        var future = CompletableFuture.supplyAsync(clientService::findAll, executor);
        return Flux.<List<ClientResponseDto>>generate( sink -> sink.next(clientService.findAll()))
                .delayElements(Duration.ofSeconds(1), timer)
                .doOnNext(val -> log.info("val:{}", val));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Mono<ClientResponseDto> create(
            @RequestBody ClientRequestDto client) {
        return Mono.fromFuture(CompletableFuture
                .supplyAsync(() -> clientService.create(client), executor));
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.PUT)
    public Mono<ClientResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody ClientRequestDto client) {
        return Mono.fromFuture(CompletableFuture
                .supplyAsync(() -> clientService.update(id, client), executor));
    }

}
