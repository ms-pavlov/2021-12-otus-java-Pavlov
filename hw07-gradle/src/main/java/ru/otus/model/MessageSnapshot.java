package ru.otus.model;

import java.util.Optional;

public class MessageSnapshot {

    private final Message snapshot;
    private final Long snapshotDate;

    public MessageSnapshot(Message snapshot, long snapshotDate) {
        this.snapshot = snapshot;
        this.snapshotDate = snapshotDate;
    }

    public Long getId() {
        return Optional.ofNullable(snapshot).map(Message::getId).orElse(null);
    }

    public Long getSnapshotDate() {
        return snapshotDate;
    }
}
