package ru.otus.cachehw.messages;

import java.util.Optional;
import java.util.ResourceBundle;

public abstract class AbstractMessages<K, V> implements CacheMessage<K, V> {
    private final K key;
    private final V value;

    public static String MESSAGES_PROPERTY = "messages";
    private String message;

    public AbstractMessages(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public abstract String getAction();

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    public String getAction(String name) {
        return Optional.ofNullable(message)
                .orElseGet(() -> {
                    message = getProperty(name);
                    return message;
                });
    }

    private static String getProperty(String propertyName) {
        return ResourceBundle.getBundle(MESSAGES_PROPERTY).getString(propertyName);
    }
}
