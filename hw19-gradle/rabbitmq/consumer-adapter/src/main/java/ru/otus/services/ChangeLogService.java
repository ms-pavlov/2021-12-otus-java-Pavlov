package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.config.RabbitConfiguration;
import ru.otus.messages.SubscriberMessage;

@Service
public class ChangeLogService {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogService.class);
    public static final String SUBSCRIBE_ROUTING_KEY = "subscriber.new";

    private final RabbitTemplate rabbitTemplate;

    public ChangeLogService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        subscribe();
    }

    private void subscribe() {
        rabbitTemplate.convertAndSend(SUBSCRIBE_ROUTING_KEY, new SubscriberMessage(RabbitConfiguration.CLIENT_ID, RabbitConfiguration.CHANGE_LOG_ROUTING_KEY));
    }
}
