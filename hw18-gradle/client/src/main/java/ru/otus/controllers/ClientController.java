package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.services.ClientService;

@Controller
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @MessageMapping("/clients/create")
    @SendTo("/topic/clients/create")
    public Mono<ClientResponseDto> create(ClientRequestDto request) {
        return clientService.create(request);
    }

    @MessageMapping("/clients/update.{id}")
    @SendTo("/topic/clients/update")
    public Mono<ClientResponseDto> create(@DestinationVariable Long id, ClientRequestDto request) {
        return clientService.update(id, request);
    }
}
