package ru.otus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObjectForMessage {
    private List<String> data;

    public ObjectForMessage() {
        this.data = new ArrayList<>();
    }

    public ObjectForMessage(ObjectForMessage object) {
        this.data = new ArrayList<>(Optional
                .ofNullable(object)
                .map(ObjectForMessage::getData)
                .orElse(new ArrayList<>()));
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" + data + "}";
    }
}
