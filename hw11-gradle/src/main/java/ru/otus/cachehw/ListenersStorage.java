package ru.otus.cachehw;

import ru.otus.cachehw.messages.CacheMessage;

public interface ListenersStorage<K, V> {
    void add(HwListener<K, V> listener);
    void remove(HwListener<K, V> listener);
    void clean();
    void notifyAll(CacheMessage<K, V> message);
}
