package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> history;

    public HistoryListener() {
        this.history = new TreeMap<>();
    }

    @Override
    public void onUpdated(Message msg) {
        history.put(System.nanoTime(), msg.copy());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return history.values().stream().filter(message -> message.getId() == id).findFirst();
    }
}