package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.dto.request.ClientRequest;
import ru.otus.dto.response.ClientResponse;
import ru.otus.services.ClientService;
import ru.otus.services.JdbcService;

@Controller
@RequestMapping("/")
public class IndexController {

    private final JdbcService<ClientResponse, ClientRequest> service;

    public IndexController(ClientService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("clients", service.findAll());
        return "index";
    }

    @RequestMapping("/addclient/")
    public String showClientForm(Model model) {
        return "addclient";
    }
}
