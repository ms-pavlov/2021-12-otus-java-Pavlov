package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.controllers.ClientController;
import ru.otus.services.executers.WebCommandFactory;
import ru.otus.services.request.WebRequestFactory;

import java.util.List;

public class SameNetService<R, Q> implements NetService<R, Q> {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private final WebClient client;
    private final WebCommandFactory<Q> commandFactory;
    private final WebRequestFactory<Q> requestFactory;

    public SameNetService(WebClient customWebClient,
                          WebCommandFactory<Q> commandFactory,
                          WebRequestFactory<Q> requestFactory) {
        this.client = customWebClient;
        this.commandFactory = commandFactory;
        this.requestFactory = requestFactory;
    }

    @Override
    public Flux<List<R>> findAll() {
        return commandFactory.prepGet()
                .execute(client, requestFactory.prepGetRequest())
                .bodyToFlux(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public Mono<R> create(Q request) {
        log.info("create");
        return commandFactory.prepPost()
                .execute(client, requestFactory.prepPostRequest(request))
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public Mono<R> update(Long id, Q request) {
        return commandFactory.prepPut()
                .execute(client, requestFactory.prepPutRequest(id, request))
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
