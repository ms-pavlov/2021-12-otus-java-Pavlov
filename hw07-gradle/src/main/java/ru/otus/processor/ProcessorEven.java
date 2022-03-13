package ru.otus.processor;

import ru.otus.model.Message;
import ru.otus.model.MessageSnapshot;

import java.util.Optional;

public class ProcessorEven implements Processor {
    private MessageSnapshot snapshot;
    private final TimeStrategy timeStrategy;

    public ProcessorEven(TimeStrategy timeStrategy) {
        this.timeStrategy = timeStrategy;
    }

    @Override
    public Message process(Message message) {
        var currentTime = timeStrategy.getCurrentTime();
        snapshot = message.createSnapshot(currentTime);

        if ((currentTime&1) == 0) {
            throw new RuntimeException("Четная секунда");
        }

        return message;
    }

    public Long getId() {
        return Optional.ofNullable(snapshot)
                .map(MessageSnapshot::getId)
                .orElse(null);
    }

    public Long getSnapshotDate() {
        return Optional.ofNullable(snapshot)
                .map(MessageSnapshot::getSnapshotDate)
                .orElse(null);
    }
}
