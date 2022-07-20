package ru.otus.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;
import ru.otus.mappers.ResponseMapper;
import ru.otus.service.RestForCRUD;
import ru.otus.service.RestForCRUDService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class SimpleRestForCRUDController<M, R, Q> {

    private final RestForCRUD<M, Q> service;
    private final SimpleMonoMaker<R> monoMaker;
    private final ExecutorService executor;

    private final ResponseMapper<M, R> responseMapper;


    @Autowired
    public SimpleRestForCRUDController(RestForCRUD<M, Q> service, ExecutorService executor, ResponseMapper<M, R> responseMapper) {
        this.service = service;
        this.executor = executor;
        this.responseMapper = responseMapper;
        this.monoMaker = new SimpleMonoMaker<>();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Mono<List<R>> findAll() {
        return monoMaker.makeMonoList(()-> service.findAll().stream().map(responseMapper::toResponse).toList(), executor);
    }

    @RequestMapping(value = "/page/{page}/{size}/", method = RequestMethod.GET)
    public Mono<Page<R>> findPageable(
            @PathVariable("page") int page,
            @PathVariable("size") int size) {
        var models = service.findPageable(PageRequest.of(page, size));
        var responses = new PageImpl<>(
                models.stream()
                        .map(responseMapper::toResponse)
                        .toList(),
                models.getPageable(),
                models.getTotalElements());
        return monoMaker.makeMonoPage(() -> responses, executor);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public Mono<R> findOne(
            @PathVariable("id") Long id) {
        return monoMaker.makeMono(() -> toResponse(service.findOne(id)), executor);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Mono<R> create(
            @RequestBody Q request) {
        return monoMaker.makeMono(() -> toResponse(service.create(request)), executor);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.PUT)
    public Mono<R> update(
            @PathVariable("id") Long id,
            @RequestBody Q request) {
        return monoMaker.makeMono(() -> toResponse(service.update(id, request)), executor);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public Mono<R> delete(
            @PathVariable("id") Long id) {
        return monoMaker.makeMono(() -> toResponse(service.delete(id)), executor);
    }

    private R toResponse(M model) {
        return Optional.of(model).map(responseMapper::toResponse).orElse(null);
    }

    public RestForCRUD<M, Q> getService() {
        return service;
    }

    public ResponseMapper<M, R> getResponseMapper() {
        return responseMapper;
    }

    public SimpleMonoMaker<R> getMonoMaker() {
        return monoMaker;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
