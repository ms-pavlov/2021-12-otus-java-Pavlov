package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.otus.queue.MessagesQueue;

@Service
public class ChangeLogService {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogService.class);

    private final MessagesQueue<String> messagesQueue;

    @Autowired
    public ChangeLogService(MessagesQueue<String> messagesQueue) {
        this.messagesQueue = messagesQueue;
    }

    public void send(String message) {
        messagesQueue.send(message);
    }

    public Flux<String> getFlux() {
        return Flux.create(messagesQueue::addSubscriber);
    }
}
