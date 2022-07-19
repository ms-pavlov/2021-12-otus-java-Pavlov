package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buildings/")
public class BuildingsController {
    @RequestMapping("/")
    public String showMainPage(Model model) {
        return "buildings/index";
    }

    @RequestMapping("/create")
    public String showCreatePage(Model model) {
        return "buildings/create";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdatePage(
            @PathVariable("id") Long id,
            Model model) {
        model.addAttribute("id", id);
        return "buildings/update";
    }
}
