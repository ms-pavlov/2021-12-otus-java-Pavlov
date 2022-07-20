package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.otus.client.NetService;
import ru.otus.controllers.interfaces.SimpleCRUDController;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.responses.BuildingsResponse;

@Controller
@MessageMapping("/buildings")
public class BuildingsRestController extends SimpleCRUDController<BuildingsResponse, BuildingsRequest> {
    private static final Logger log = LoggerFactory.getLogger(BuildingsRestController.class);
    private static final String TOPIC = "/topic/buildings/all";

    public BuildingsRestController(NetService<BuildingsResponse, BuildingsRequest> service,
                                   MessageSendingOperations<String> template,
                                   Scheduler timer) {
        super(service, template, timer);
    }

    @Override
    @MessageMapping("/{id}")
    @SendTo("/topic/buildings/find")
    public Mono<BuildingsResponse> find(
            @DestinationVariable Long id) {
        log.info("find element {}", id);
        return getService().findOne(id);
    }

    @Override
    @MessageMapping("/create")
    @SendTo("/topic/buildings/create")
    public Mono<BuildingsResponse> create(BuildingsRequest request) {
        return getService().create(request);
    }

    @Override
    @MessageMapping("/update/{id}")
    @SendTo("/topic/buildings/update")
    public Mono<BuildingsResponse> update(@DestinationVariable Long id, BuildingsRequest request) {
        return getService().update(id, request);
    }

    @Override
    public String getMainTopic() {
        return TOPIC;
    }
}
