package ru.otus.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.messages.PlacementsMessage;
import ru.otus.services.ChangeLogService;

@RestController
@RequestMapping(value = "/api/changes/",
        produces = MediaType.APPLICATION_NDJSON_VALUE)
public class ChangeLogController {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogController.class);
    private final ChangeLogService changeLogService;

    @Autowired
    public ChangeLogController(ChangeLogService changeLogService) {
        this.changeLogService = changeLogService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Flux<PlacementsMessage> getChangeLog() {
        return changeLogService.getFlux();
    }
}
