package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.request.ClientRequest;
import ru.otus.dto.response.ClientResponse;
import ru.otus.services.ClientService;
import ru.otus.services.JdbcService;

import java.util.Optional;

@Controller
@RequestMapping(value ="/api/clients/",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientsController {
    private static final Logger log = LoggerFactory.getLogger(ClientsController.class);
    private final JdbcService<ClientResponse, ClientRequest> clientService;

    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String create(
            Model model,
            @ModelAttribute("client") ClientRequest client) {
        return Optional.ofNullable(clientService.create(client))
                .map(clientResponse -> "redirect:/")
                .orElse("redirect:/addclient/");
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.PUT)
    public String update(
            Model model,
            @RequestParam("id") Long id,
            @ModelAttribute("client") ClientRequest client) {
        return Optional.ofNullable(clientService.update(id, client))
                .map(clientResponse -> "redirect:/")
                .orElse("redirect:/addclient/");
    }

}
