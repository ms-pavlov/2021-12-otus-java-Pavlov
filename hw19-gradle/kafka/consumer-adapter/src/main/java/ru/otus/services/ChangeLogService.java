package ru.otus.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.otus.messages.PlacementsMessage;

import static ru.otus.config.KafkaConsumerConfig.CHANGE_LOG_TOPIC;

@Service
public class ChangeLogService {

    private static final Logger log = LoggerFactory.getLogger(ChangeLogService.class);

    @KafkaListener(topics = CHANGE_LOG_TOPIC, containerFactory="kafkaListenerContainerFactory")
    public void listenGroupFoo(ConsumerRecord<String, String> message) {
        log.info("received: {}", message.value());
    }
}
