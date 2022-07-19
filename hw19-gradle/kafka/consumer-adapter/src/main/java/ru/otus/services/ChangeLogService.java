package ru.otus.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.adapter.ConsumerWebSender;

import static ru.otus.config.KafkaConsumerConfig.CHANGE_LOG_TOPIC;
import static ru.otus.config.KafkaConsumerConfig.CLIENT_ID;

@Service
public class ChangeLogService {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogService.class);
    private final static MediaType MEDIA_TYPE = MediaType.APPLICATION_NDJSON;

    private final ConsumerWebSender sender;

    public ChangeLogService(WebClient client,
                            ObjectMapper mapper) {
        sender = new ConsumerWebSender(client, mapper);
    }

    @KafkaListener(topics = CHANGE_LOG_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupFoo(ConsumerRecord<String, String> message) {
        sender.send(CLIENT_ID, message.value());
        log.info("received: {}", message.value());
    }

}
