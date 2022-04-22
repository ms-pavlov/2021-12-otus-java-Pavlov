package ru.otus.cachehw;


import ru.otus.cachehw.messages.GetMessages;
import ru.otus.cachehw.messages.PutMessages;
import ru.otus.cachehw.messages.RemoveMessages;

import java.util.Optional;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final WeakHashMap<CacheKey<K>, V> cache;
    private final ListenersStorage<K, V> listeners;

    public MyCache() {
        this(new SimpleListenersStorage<>());
    }

    public MyCache(ListenersStorage<K, V> listeners) {
        this.cache = new WeakHashMap<>();
        this.listeners = listeners;
    }

    @Override
    public void put(K key, V value) {
        listeners.notifyAll(new PutMessages<>(key, value));
        cache.put(new CacheKey<>(key), value);
    }

    @Override
    public void remove(K key) {
        var cacheKey = new CacheKey<>(key);
        Optional.ofNullable(cache.get(cacheKey))
                .ifPresent(value -> {
                    listeners.notifyAll(new RemoveMessages<>(key, value));
                    cache.remove(cacheKey);
                });
    }

    @Override
    public V get(K key) {
        return Optional.ofNullable(cache.get(new CacheKey<>(key)))
                .map(value -> {
                    listeners.notifyAll(new GetMessages<>(key, value));
                    return value;
                }).orElse(null);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
