package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.services.ClientService;
import ru.otus.services.JdbcService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/api/clients/",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientsController {
    private static final Logger log = LoggerFactory.getLogger(ClientsController.class);
    private final JdbcService<ClientResponseDto, ClientRequestDto> clientService;
    private final ExecutorService executor;

    public ClientsController(ClientService clientService, ExecutorService executor) {
        this.clientService = clientService;
        this.executor = executor;
    }

    @GetMapping(value = "/all/")
    public Flux<ClientResponseDto> findAll() {
        return Mono.fromFuture(CompletableFuture.supplyAsync(clientService::findAll, executor))
                .flatMapMany(clientResponses -> Flux.fromStream(clientResponses.stream()));
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
