package ru.otus.processor;

import ru.otus.model.MementoMessage;
import ru.otus.model.Message;
import ru.otus.model.MessageSnapshot;

import java.util.Optional;

public class ProcessorEven implements Processor, MementoMessage {
    private MessageSnapshot snapshot;

    @Override
    public Message process(Message message) {
        var currentTime = System.nanoTime() / 1_000_000_000;
        snapshot = message.createSnapshot(currentTime);

        if ((currentTime&1) == 0) {
            throw new RuntimeException("Четная секунда");
        }

        return message;
    }

    @Override
    public Long getId() {
        return Optional.ofNullable(snapshot)
                .map(MessageSnapshot::getId)
                .orElse(null);
    }

    @Override
    public Long getSnapshotDate() {
        return Optional.ofNullable(snapshot)
                .map(MessageSnapshot::getSnapshotDate)
                .orElse(null);
    }
}
