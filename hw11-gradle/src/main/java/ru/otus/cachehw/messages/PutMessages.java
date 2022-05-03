package ru.otus.cachehw.messages;

public class PutMessages<K, V> extends AbstractMessages<K, V>{

    public PutMessages(K key, V value) {
        super(key, value);
    }

    @Override
    public String getAction() {
        return getAction("put");
    }
}
