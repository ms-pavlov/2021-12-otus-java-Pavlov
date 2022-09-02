package ru.otus.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    public static final String MAIN_EXCHANGE = "main-exchange";

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
    public Queue newSubscriberEventsQueue() {
        return new Queue("new-subscriber-queue");
    }

    @Bean
    public Binding onNewSubscriberEventsQueueBinding() {
        return BindingBuilder.bind(newSubscriberEventsQueue())
                .to(topicExchange())
                .with("subscriber.new");
    }
}
