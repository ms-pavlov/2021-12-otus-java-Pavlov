package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.messages.PlacementsMessage;
import ru.otus.services.ChangeLogService;

@RestController
public class ChangeLogController {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogController.class);
    private static final String TOPIC = "/topic/changes/";
    private final ChangeLogService service;

    public ChangeLogController(ChangeLogService service,
                               MessageSendingOperations<String> template) {
        this.service = service;
        service.getFlux()
                .doOnNext(placementsMessage -> log.info("send WS {}: {}", TOPIC, placementsMessage))
                .subscribe(placementsMessage -> template.convertAndSend(TOPIC, placementsMessage));
    }

    @RequestMapping(value = "/log/{guid}", method = RequestMethod.PUT)
    public Mono<String> endpoint(
            @PathVariable("guid") String guid,
            @RequestBody PlacementsMessage request) {
        var messageString = String.format("message from %s: %s", guid, request);
        service.send(messageString);
        return Mono.just(messageString);
    }

}
