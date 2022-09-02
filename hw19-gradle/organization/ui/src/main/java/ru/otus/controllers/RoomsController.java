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
import ru.otus.dto.requests.RoomsRequest;
import ru.otus.dto.responses.PlacementsResponse;
import ru.otus.dto.responses.RoomsResponse;

@Controller
public class RoomsController extends SimpleCRUDController<RoomsResponse, RoomsRequest>  {

    private static final String TOPIC = "/topic/rooms/all";

    @Autowired
    public RoomsController(NetService<RoomsResponse, RoomsRequest> service,
                                MessageSendingOperations<String> template,
                                Scheduler timer) {
        super(service, template, timer);
    }

    @Override
    public String getMainTopic() {
        return TOPIC;
    }

    @RequestMapping("/rooms/")
    public String showMainPage(Model model) {
        return "rooms/index";
    }

    @RequestMapping("/rooms/create")
    public String showCreatePage(Model model) {
        return "rooms/create";
    }

    @Override
    @MessageMapping("/rooms/{id}")
    @SendTo("/topic/rooms/find")
    public Mono<RoomsResponse> find(
            @DestinationVariable Long id) {
        return getService().findOne(id);
    }

    @Override
    @MessageMapping("/rooms/create")
    @SendTo("/topic/rooms/create")
    public Mono<RoomsResponse> create(RoomsRequest request) {
        return getService().create(request);
    }

    @Override
    @MessageMapping("/rooms/update/{id}")
    @SendTo("/topic/rooms/update")
    public Mono<RoomsResponse> update(@DestinationVariable Long id, RoomsRequest request) {
        return getService().update(id, request);
    }
}
