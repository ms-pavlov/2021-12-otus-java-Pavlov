package ru.otus.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.otus.messages.PlacementsMessage;
import ru.otus.config.KafkaConsumerConfig;

@Service
public class ChangeLogService {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogService.class);

    @KafkaListener(topics = KafkaConsumerConfig.CHANGE_LOG_TOPIC)
    public void listenGroupFoo(ConsumerRecord<Long, PlacementsMessage> message) {
        log.info("received: {}", message.value());
    }
}
