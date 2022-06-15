package ru.otus.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.services.ClientService;

import java.time.Duration;
import java.util.Optional;

@Controller
public class ClientsListController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private static final String TOPIC = "/topic/clients";
    private final MessageSendingOperations<String> template;
    private final ClientService clientService;


    public ClientsListController(MessageSendingOperations<String> template,
                                 ClientService clientService) {
        this.template = template;
        this.clientService = clientService;
    }

    @Scheduled
    public void broadcastClientsList() {
        log.info("broadcastClientsList start");
        var responseFlux = clientService.findAll()
                .doOnNext(clients -> template.convertAndSend(TOPIC, clients))
                .doOnNext(clients -> log.info("clients{}", clients));
    }

}
