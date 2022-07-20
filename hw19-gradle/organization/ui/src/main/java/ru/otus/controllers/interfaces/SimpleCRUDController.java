package ru.otus.controllers.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import reactor.core.scheduler.Scheduler;
import ru.otus.client.NetService;

import java.util.concurrent.TimeUnit;

public abstract class SimpleCRUDController<R, Q> implements CRUDController<R, Q> {

    private static final Logger log = LoggerFactory.getLogger(SimpleCRUDController.class);
    private final NetService<R, Q> service;

    public SimpleCRUDController(NetService<R, Q> service,
                                MessageSendingOperations<String> template,
                                Scheduler timer) {
        this.service = service;

        log.info("list {}", this.service.findAll().block());

        timer.schedulePeriodically(
                () -> service.findAll()
                        .subscribe(responses -> template.convertAndSend(getMainTopic(), responses)),
                0, 1, TimeUnit.SECONDS);
    }


    public abstract String getMainTopic();

    @Override
    public NetService<R, Q> getService() {
        return service;
    }
}
