package ru.otus.cachehw;

import ru.otus.cachehw.messages.CacheMessage;

import java.util.ArrayList;
import java.util.List;

public class SimpleListenersStorage<K, V> implements ListenersStorage<K, V> {
    private final List<HwListener<K, V>> listeners;

    public SimpleListenersStorage() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void add(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void remove(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    @Override
    public void clean() {
        listeners.clear();
    }

    @Override
    public void notifyAll(CacheMessage<K, V> message) {
        listeners.forEach(listener -> listener.notify(message.getKey(), message.getValue(), message.getAction()));
    }

}
