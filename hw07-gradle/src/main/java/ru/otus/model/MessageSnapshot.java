package ru.otus.model;

import java.util.Optional;

public class MessageSnapshot implements MementoMessage {

    private final Message snapshot;
    private final Long snapshotDate;

    public MessageSnapshot(Message snapshot, long snapshotDate) {
        this.snapshot = snapshot;
        this.snapshotDate = snapshotDate;
    }

    @Override
    public Long getId() {
        return Optional.ofNullable(snapshot).map(Message::getId).orElse(null);
    }

    @Override
    public Long getSnapshotDate() {
        return snapshotDate;
    }
}
