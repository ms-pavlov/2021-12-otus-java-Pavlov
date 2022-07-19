package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.messages.PlacementsMessage;

import java.time.Duration;
import java.util.List;

import static ru.otus.config.KafkaProducerConfig.CHANGE_LOG_TOPIC;

@Service
public class ChangeLogService {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogService.class);
    private static final String CHANGE_LOG_URL = "/api/changes/";
    private static final String PLACEMENTS_URL = "/api/placements/as/message";
    private final static MediaType MEDIA_TYPE = MediaType.APPLICATION_NDJSON;
    private final KafkaTemplate<String, PlacementsMessage> template;
    private final WebClient customWebClient;

    public ChangeLogService(KafkaTemplate<String, PlacementsMessage> template, WebClient customWebClient) {
        this.template = template;
        this.customWebClient = customWebClient;
        init();
    }

    private void init() {
        prepRequest(PLACEMENTS_URL)
                .bodyToMono(new ParameterizedTypeReference<List<PlacementsMessage>>() {})
                .delayElement(Duration.ofSeconds(1))
                .subscribe(placements -> placements
                        .forEach(this::send));
        prepRequest(CHANGE_LOG_URL)
                .bodyToFlux(PlacementsMessage.class)
                .subscribe(this::send);
    }

    private void send(PlacementsMessage message) {
        log.info("send message {}", message);
        template.send(CHANGE_LOG_TOPIC, message);
    }

    private WebClient.ResponseSpec prepRequest(String url) {
        return customWebClient
                .get()
                .uri(url)
                .accept(MEDIA_TYPE)
                .retrieve();
    }

}
