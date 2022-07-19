package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class ChangeLogController {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogController.class);
    private static final String TOPIC = "/topic/changes/";
    private static final String URL = "/api/changes/";
    private final static MediaType MEDIA_TYPE = MediaType.APPLICATION_NDJSON;

    private final WebClient customWebClient;
    private final MessageSendingOperations<String> template;

    public ChangeLogController(WebClient customWebClient,
                               MessageSendingOperations<String> template) {
        this.customWebClient = customWebClient;
        this.template = template;
        broadcastPlacementsMessage();
    }


    private void broadcastPlacementsMessage() {
        customWebClient
                .get()
                .uri(URL)
                .accept(MEDIA_TYPE)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<>() {})
                .doOnNext(message -> log.info("message {}", message))
                .subscribe(message -> template.convertAndSend(TOPIC, message));

    }
}
