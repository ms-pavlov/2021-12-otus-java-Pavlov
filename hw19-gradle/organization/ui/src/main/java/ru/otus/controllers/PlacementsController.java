package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.otus.client.NetService;
import ru.otus.controllers.interfaces.SimpleCRUDController;
import ru.otus.dto.requests.PlacementsRequest;
import ru.otus.dto.responses.PlacementsResponse;

@Controller
public class PlacementsController extends SimpleCRUDController<PlacementsResponse, PlacementsRequest> {
    private static final String TOPIC = "/topic/placements/all";

    @Autowired
    public PlacementsController(NetService<PlacementsResponse, PlacementsRequest> service,
                                MessageSendingOperations<String> template,
                                Scheduler timer) {
        super(service, template, timer);
    }

    @Override
    public String getMainTopic() {
        return TOPIC;
    }

    @RequestMapping("/placements/")
    public String showMainPage(Model model) {
        return "placements/index";
    }

    @RequestMapping("/placements/create")
    public String showCreatePage(Model model) {
        return "placements/create";
    }

    @Override
    @MessageMapping("/placements/{id}")
    @SendTo("/topic/placements/find")
    public Mono<PlacementsResponse> find(
            @DestinationVariable Long id) {
        return getService().findOne(id);
    }

    @Override
    @MessageMapping("/placements/create")
    @SendTo("/topic/placements/create")
    public Mono<PlacementsResponse> create(PlacementsRequest request) {
        return getService().create(request);
    }

    @Override
    @MessageMapping("/placements/update/{id}")
    @SendTo("/topic/placements/update")
    public Mono<PlacementsResponse> update(@DestinationVariable Long id, PlacementsRequest request) {
        return getService().update(id, request);
    }
}
