package ru.otus.queue;

import reactor.core.publisher.FluxSink;

public interface MessagesQueue<M> {
    void addSubscriber(FluxSink<M> mFluxSink);

    void flush();

    void send(M message);
}
