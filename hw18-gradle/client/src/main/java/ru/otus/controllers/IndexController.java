package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public String showMainPage(Model model) {
        return "index";
    }

    @RequestMapping("/create")
    public String showCreatePage(Model model) {
        return "addclient";
    }
}
