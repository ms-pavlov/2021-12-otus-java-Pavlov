package ru.otus.cachehw.messages;

public class RemoveMessages<K, V> extends AbstractMessages<K, V> {

    public RemoveMessages(K key, V value) {
        super(key, value);
    }

    @Override
    public String getAction() {
        return getAction("remove");
    }
}
