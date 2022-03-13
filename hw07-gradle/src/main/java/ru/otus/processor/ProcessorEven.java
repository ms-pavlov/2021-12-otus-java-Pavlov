package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorEven implements Processor {
    private final TimeStrategy timeStrategy;
    private long currentTime;

    public ProcessorEven(TimeStrategy timeStrategy) {
        this.timeStrategy = timeStrategy;
    }

    @Override
    public Message process(Message message) {
        this.currentTime = timeStrategy.getCurrentTime();
        if ((this.currentTime & 1) == 0) {
            throw new RuntimeException("Четная секунда");
        }
        return message;
    }

    public long getCurrentTime() {
        return currentTime;
    }
}
