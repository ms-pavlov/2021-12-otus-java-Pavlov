package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.request.ClientRequest;
import ru.otus.dto.response.ClientResponse;
import ru.otus.services.ClientService;
import ru.otus.services.JdbcService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/api/clients/",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientsController {
    private static final Logger log = LoggerFactory.getLogger(ClientsController.class);
    private final JdbcService<ClientResponse, ClientRequest> clientService;
    private final ExecutorService executor;

    public ClientsController(ClientService clientService, ExecutorService executor) {
        this.clientService = clientService;
        this.executor = executor;
    }

    @GetMapping(value = "/all/")
    public Flux<ClientResponse> findAll() {
        return Mono.fromFuture(CompletableFuture
                .supplyAsync(clientService::findAll, executor)).flatMapMany(clientResponses -> Flux.fromStream(clientResponses.stream()));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Mono<ClientResponse> create(
            @RequestBody ClientRequest client) {
        return Mono.fromFuture(CompletableFuture
                .supplyAsync(() -> clientService.create(client), executor));
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.PUT)
    public Mono<ClientResponse> update(
            @PathVariable("id") Long id,
            @RequestBody ClientRequest client) {
        return Mono.fromFuture(CompletableFuture
                .supplyAsync(() -> clientService.update(id, client), executor));
    }

}
