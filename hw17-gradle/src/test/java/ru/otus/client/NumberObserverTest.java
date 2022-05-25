package ru.otus.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.protobuf.generated.DataMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NumberObserverTest {

    private NumberObserver observer;
    private ObserverCompleteStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = mock(ObserverCompleteStrategy.class);
        observer = new NumberObserver(strategy);
    }

    @Test
    void onNext() {
        observer.onNext(DataMessage.newBuilder().setNumber(1L).build());
        assertEquals(1L, observer.extractCurrentNumber());
    }

    @Test
    void onCompleted() {
        observer.onCompleted();
        verify(strategy, times(1)).onCompleted();
    }

    @Test
    void extractCurrentNumber() {
        observer.onNext(DataMessage.newBuilder().setNumber(1L).build());
        assertEquals(1L, observer.extractCurrentNumber());
        assertEquals(0L, observer.extractCurrentNumber());
    }
}