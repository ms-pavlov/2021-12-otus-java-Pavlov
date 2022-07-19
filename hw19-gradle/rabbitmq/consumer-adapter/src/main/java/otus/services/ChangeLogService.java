package otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.messages.SubscriberMessage;

import static otus.config.RabbitConfiguration.CHANGE_LOG_ROUTING_KEY;
import static otus.config.RabbitConfiguration.CLIENT_ID;

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
        rabbitTemplate.convertAndSend(SUBSCRIBE_ROUTING_KEY, new SubscriberMessage(CLIENT_ID, CHANGE_LOG_ROUTING_KEY));
    }
}
