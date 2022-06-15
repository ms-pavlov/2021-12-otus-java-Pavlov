package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.services.ClientService;

@Controller
public class ClientsListController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private final SimpMessagingTemplate template;
    private final ClientService clientService;

    public ClientsListController(SimpMessagingTemplate template, ClientService clientService) {
        this.template = template;
        this.clientService = clientService;
    }

    @Scheduled(fixedDelay = 1000)
    public void broadcastClientsList() {
        var result = clientService.findAll();
        result.log();
        template.convertAndSend("/topic/clients", result);
    }
}
