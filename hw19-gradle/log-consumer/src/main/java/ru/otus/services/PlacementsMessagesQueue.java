package ru.otus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Scheduler;
import ru.otus.queue.SimpleMessagesQueue;

@Component
public class PlacementsMessagesQueue extends SimpleMessagesQueue<String> {
    @Autowired
    public PlacementsMessagesQueue(Scheduler timer) {
        super(timer);
    }
}
