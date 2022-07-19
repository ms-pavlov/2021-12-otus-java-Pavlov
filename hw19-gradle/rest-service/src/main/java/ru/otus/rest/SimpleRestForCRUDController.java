package ru.otus.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;
import ru.otus.service.RestForCRUDService;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class SimpleRestForCRUDController<E, M, R, Q> {

    private final RestForCRUDService<E, M, R, Q> service;
    private final SimpleMonoMaker<R> monoMaker;
    private final ExecutorService executor;

    @Autowired
    public SimpleRestForCRUDController(RestForCRUDService<E, M, R, Q> service, ExecutorService executor) {
        this.service = service;
        this.executor = executor;
        this.monoMaker = new SimpleMonoMaker<>();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Mono<List<R>> findAll() {
        return monoMaker.makeMonoList(service::findAll, executor);
    }

    @RequestMapping(value = "/page/{page}/{size}/", method = RequestMethod.GET)
    public Mono<Page<R>> findPageable(
            @PathVariable("page") int page,
            @PathVariable("size") int size) {
        return monoMaker.makeMonoPage(() -> service.findPageable(PageRequest.of(page, size)), executor);
    }
    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public Mono<R> findOne(
            @PathVariable("id") Long id) {
        return monoMaker.makeMono(() -> service.findOne(id), executor);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Mono<R> create(
            @RequestBody Q request) {
        return monoMaker.makeMono(() -> service.create(request), executor);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.PUT)
    public Mono<R> update(
            @PathVariable("id") Long id,
            @RequestBody Q request) {
        return monoMaker.makeMono(() -> service.update(id, request), executor);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public Mono<R> delete(
            @PathVariable("id") Long id) {
        return monoMaker.makeMono(() -> service.delete(id), executor);
    }

    public RestForCRUDService<E, M, R, Q> getService() {
        return service;
    }

    public SimpleMonoMaker<R> getMonoMaker() {
        return monoMaker;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
