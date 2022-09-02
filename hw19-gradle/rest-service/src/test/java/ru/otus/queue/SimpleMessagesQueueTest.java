package ru.otus.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;


class SimpleMessagesQueueTest {
    private static final TestMessage MESSAGE = new TestMessage("test");
    private static final int INITIAL_DELAY = 0;
    private static final int QUEUE_CHECK_PERIOD = 1;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private SimpleMessagesQueue<TestMessage> messagesQueue;

    @BeforeEach
    void setUp() {
        Scheduler timer = Schedulers.newParallel("scheduler", 2);
        messagesQueue = new SimpleMessagesQueue<>(timer);
    }

    @Test
    void checkSendToFlux() {
        FluxSink<TestMessage> fuxSink = mock(FluxSink.class);
        messagesQueue.addSubscriber(fuxSink);

        messagesQueue.send(MESSAGE);

        messagesQueue.flush();

        verify(fuxSink, times(1)).next(MESSAGE);
    }

}