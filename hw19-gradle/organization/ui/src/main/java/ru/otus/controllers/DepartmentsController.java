package ru.otus.controllers;

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
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.dto.responses.DepartmentsResponse;

@Controller
public class DepartmentsController extends SimpleCRUDController<DepartmentsResponse, DepartmentsRequest> {
    private static final String TOPIC = "/topic/departments/all";

    public DepartmentsController(NetService<DepartmentsResponse, DepartmentsRequest> service,
                                 MessageSendingOperations<String> template,
                                 Scheduler timer) {
        super(service, template, timer);
    }

    @Override
    public String getMainTopic() {
        return TOPIC;
    }

    @RequestMapping("/departments/")
    public String showMainPage(Model model) {
        return "departments/index";
    }

    @RequestMapping("/departments/create")
    public String showCreatePage(Model model) {
        return "departments/create";
    }

    @Override
    @MessageMapping("/departments/{id}")
    @SendTo("/topic/departments/find")
    public Mono<DepartmentsResponse> find(
            @DestinationVariable Long id) {
        return getService().findOne(id);
    }

    @Override
    @MessageMapping("/departments/create")
    @SendTo("/topic/departments/create")
    public Mono<DepartmentsResponse> create(DepartmentsRequest request) {
        return getService().create(request);
    }

    @Override
    @MessageMapping("/departments/update/{id}")
    @SendTo("/topic/departments/update")
    public Mono<DepartmentsResponse> update(@DestinationVariable Long id, DepartmentsRequest request) {
        return getService().update(id, request);
    }
}
