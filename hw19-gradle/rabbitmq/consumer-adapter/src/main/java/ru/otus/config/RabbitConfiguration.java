package ru.otus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.adapter.ConsumerWebSender;
import ru.otus.services.ChangeLogService;

import java.util.UUID;

@Configuration
public class RabbitConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogService.class);
    public static final String MAIN_EXCHANGE = "main-exchange";
    public static final String CLIENT_ID = UUID.randomUUID().toString();
    public static final String CHANGE_LOG_ROUTING_KEY = String.format("placements.%s", CLIENT_ID);
    public static final String CHANGE_LOG_QUEUE = String.format("change-log-queue-%s", CLIENT_ID);

    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyTimeout(15000);
        rabbitTemplate.setMessageConverter(jsonConverter());
        rabbitTemplate.setExchange(MAIN_EXCHANGE);
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MAIN_EXCHANGE, false, false);
    }

    @Bean
    public Queue newChangeLogEventsQueue() {
        return new Queue(CHANGE_LOG_QUEUE);
    }

    @Bean
    public Binding onNewSubscriberEventsQueueBinding() {
        return BindingBuilder.bind(newChangeLogEventsQueue())
                .to(topicExchange())
                .with(CHANGE_LOG_ROUTING_KEY);
    }

    @Bean
    public ConsumerWebSender sender(WebClient client, ObjectMapper mapper) {
        return new ConsumerWebSender(client, mapper);
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, ConsumerWebSender sender) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(CHANGE_LOG_QUEUE);
        container.setMessageListener(message -> sender.send(CLIENT_ID, new String(message.getBody())));
        return container;
    }


}
