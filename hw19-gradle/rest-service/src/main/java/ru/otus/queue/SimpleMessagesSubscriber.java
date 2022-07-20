package ru.otus.queue;

import reactor.core.publisher.FluxSink;

public class SimpleMessagesSubscriber<M> implements MessagesSubscriber<M> {
    private boolean active;
    private final FluxSink<M> FluxSink;

    public SimpleMessagesSubscriber(FluxSink<M> mFluxSink) {
        this.FluxSink = mFluxSink;
        this.active = true;

        mFluxSink.onCancel(this::cansel);
    }

    private void cansel() {
        this.active = false;
    }

    @Override
    public boolean isCansel() {
        return !this.active;
    }

    @Override
    public void send(M message) {
        FluxSink.next(message);
    }
}
