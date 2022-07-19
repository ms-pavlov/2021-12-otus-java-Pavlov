package ru.otus.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SimpleMessagesQueue<M> implements MessagesQueue<M> {
    private static final Logger log = LoggerFactory.getLogger(SimpleMessagesQueue.class);
    private static final int INITIAL_DELAY = 0;
    private static final int QUEUE_CHECK_PERIOD = 1;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final int QUEUE_CAPACITY = 1000;

    private final BlockingQueue<M> sensorsDataQueue;
    private final List<SimpleMessagesSubscriber<M>> messagesSubscribers;

    public SimpleMessagesQueue(Scheduler timer) {
        sensorsDataQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
        messagesSubscribers = new ArrayList<>();
        timer.schedulePeriodically(this::flush, INITIAL_DELAY, QUEUE_CHECK_PERIOD, TIME_UNIT);
    }

    private void sendAll(M message) {
        messagesSubscribers.removeIf(SimpleMessagesSubscriber::isCansel);
        messagesSubscribers.forEach(subscriber -> subscriber.send(message));
    }

    @Override
    public synchronized void addSubscriber(FluxSink<M> mFluxSink) {
        messagesSubscribers.add(new SimpleMessagesSubscriber<>(mFluxSink));
    }

    @Override
    public synchronized void flush() {
        List<M> bufferedData = new ArrayList<>();
        sensorsDataQueue.drainTo(bufferedData);
        bufferedData.forEach(this::sendAll);
    }

    @Override
    public synchronized void send(M message) {
        sensorsDataQueue.offer(message);
    }
}
