package ru.otus.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Scheduler;
import ru.otus.messages.PlacementsMessage;
import ru.otus.queue.SimpleMessagesQueue;

@Component
public class PlacementsMessagesQueue extends SimpleMessagesQueue<PlacementsMessage> {
    @Autowired
    public PlacementsMessagesQueue(Scheduler timer) {
        super(timer);
    }
}
