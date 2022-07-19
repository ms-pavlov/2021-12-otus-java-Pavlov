package otus.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.messages.PlacementsMessage;
import ru.otus.messages.SubscriberMessage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChangeLogService {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogService.class);
    private static final String CHANGE_LOG_URL = "/api/changes/";
    private static final String PLACEMENTS_URL = "/api/placements/as/message";
    private final static MediaType MEDIA_TYPE = MediaType.APPLICATION_NDJSON;

    private final List<String> routingKeys = new ArrayList<>();

    private final RabbitTemplate rabbitTemplate;
    private final WebClient customWebClient;

    public ChangeLogService(WebClient customWebClient,
                            MessageSendingOperations<String> template,
                            RabbitTemplate rabbitTemplate,
                            ObjectMapper mapper) {
        this.customWebClient = customWebClient;
        this.rabbitTemplate = rabbitTemplate;
        startChangeLog(customWebClient, template);
    }

    private void startChangeLog(WebClient customWebClient, MessageSendingOperations<String> template) {
        prepRequest(CHANGE_LOG_URL)
                .bodyToFlux(PlacementsMessage.class)
                .subscribe(this::send);
    }

    private WebClient.ResponseSpec prepRequest(String url) {
        return customWebClient
                .get()
                .uri(url)
                .accept(MEDIA_TYPE)
                .retrieve();
    }

    private void send(PlacementsMessage message) {
        routingKeys.forEach(routKey -> rabbitTemplate.convertAndSend(routKey, message));
    }

    @RabbitListener(queues = "new-subscriber-queue")
    public void newClientsEventsQueueListener(SubscriberMessage subscriber) {
        log.info("new subscriber queue message {}", subscriber);
        routingKeys.add(subscriber.getRoutingKey());
        prepRequest(PLACEMENTS_URL)
                .bodyToMono(new ParameterizedTypeReference<List<PlacementsMessage>>() {})
                .delayElement(Duration.ofSeconds(1))
                .subscribe(placements -> placements
                        .forEach(message -> {
                            log.info("send new subscriber {}", message);
                            rabbitTemplate.convertAndSend(subscriber.getRoutingKey(), message);
                        }));
    }

}
