package ru.otus.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.client.request.SimpleWebRequest;
import ru.otus.messages.PlacementsMessage;

public class ConsumerWebSender {
    private static final Logger log = LoggerFactory.getLogger(ConsumerWebSender.class);
    private final static MediaType MEDIA_TYPE = MediaType.APPLICATION_NDJSON;
    private final WebClient client;
    private final ObjectMapper mapper;

    public ConsumerWebSender(WebClient client,
                             ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public void send(String clientID, String message) {
        var request = new SimpleWebRequest<>("/log/" + clientID, parse(message));
        log.info("received: {}", message);
        client.put()
                .uri(request.getUrl())
                .accept(MEDIA_TYPE)
                .body(request.getBodyInserter())
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(throwable -> log.info("request fail {}", throwable.getMessage()))
                .subscribe(log::info);
    }

    private PlacementsMessage parse(String message) {
        try {
            return mapper.readValue(message, PlacementsMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
