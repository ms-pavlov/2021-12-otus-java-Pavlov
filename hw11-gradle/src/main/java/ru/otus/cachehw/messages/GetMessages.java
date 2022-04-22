package ru.otus.cachehw.messages;


public class GetMessages<K, V> extends AbstractMessages<K, V> {

    public GetMessages(K key, V value) {
        super(key, value);
    }

    @Override
    public String getAction() {
        return getAction("get");
    }
}
