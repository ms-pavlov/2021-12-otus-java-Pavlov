package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Controller;
import ru.otus.services.ClientService;

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
        broadcastClientsList();
    }


    public void broadcastClientsList() {
        log.info("broadcastClientsList start");
        var responseFlux = clientService.findAll()
                .subscribe(clients -> {
                    log.info("clients{}", clients);
                    template.convertAndSend(TOPIC, clients);
                });
    }

}
