package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.services.ClientService;
import ru.otus.services.JdbcService;

import java.util.List;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/api/clients/",
        produces = MediaType.APPLICATION_NDJSON_VALUE)
public class ClientsController {
    private static final Logger log = LoggerFactory.getLogger(ClientsController.class);
    private final JdbcService<ClientResponseDto, ClientRequestDto> clientService;
    private final ExecutorService executor;
    private final FluxSource<ClientResponseDto> source;

    public ClientsController(ClientService clientService, ExecutorService executor, Scheduler timer) {
        this.clientService = clientService;
        this.executor = executor;
        this.source = new ClientSource(clientService, timer);
    }

    @GetMapping(value = "/all/")
    public Flux<List<ClientResponseDto>> findAll() {
        return source.getFlux();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Mono<ClientResponseDto> create(
            @RequestBody ClientRequestDto client) {
        return source.makeMono(() -> clientService.create(client), executor);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.PUT)
    public Mono<ClientResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody ClientRequestDto client) {
        return source.makeMono(() -> clientService.update(id, client), executor);
    }

}
