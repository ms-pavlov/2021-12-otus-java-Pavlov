package ru.otus.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.DataMessage;

public class NumberObserver implements StreamObserver<DataMessage> {
    private static final Logger log = LoggerFactory.getLogger(NumberObserver.class);

    private final CurrentNumber number;
    private final ObserverCompleteStrategy completeStrategy;

    public NumberObserver(ObserverCompleteStrategy completeStrategy) {
        this.number = new CurrentNumber();
        this.completeStrategy = completeStrategy;
    }

    @Override
    public void onNext(DataMessage value) {
        number.setNumber(value.getNumber());
        log.info("new value: {}", value.getNumber());
    }

    @Override
    public void onError(Throwable t) {
        log.error(t.getLocalizedMessage());
    }

    @Override
    public void onCompleted() {
        log.info("request completed");
        completeStrategy.onCompleted();
    }

    public synchronized long extractCurrentNumber() {
        var result = number.getNumber();
        number.setNumber(0);
        return result;
    }
}
