package ru.otus.cachehw.messages;

public interface CacheMessage<K, V> {
    String getAction();
    K getKey();
    V getValue();
}
