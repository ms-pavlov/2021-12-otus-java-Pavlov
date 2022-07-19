package ru.otus.queue;

public interface MessagesSubscriber<M> {
    boolean isCansel();

    void send(M message);
}
