package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.services.ClientService;

@Controller
@RequestMapping("/")
public class IndexController {

    private final ClientService service;

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
