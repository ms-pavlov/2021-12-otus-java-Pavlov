package ru.otus.config;

import reactor.util.annotation.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class SimplerThreadFactory implements ThreadFactory {
    private final AtomicLong threadIdGenerator = new AtomicLong(0);
    private final String namePrefix;

    public SimplerThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(@NonNull Runnable task) {
        return new Thread(task, namePrefix + threadIdGenerator.incrementAndGet());
    }
}
