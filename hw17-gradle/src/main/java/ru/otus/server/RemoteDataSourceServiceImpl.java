package ru.otus.server;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.Server;
import ru.otus.protobuf.generated.DataMessage;
import ru.otus.protobuf.generated.Interval;
import ru.otus.protobuf.generated.RemoteDataSourceServiceGrpc;

public class RemoteDataSourceServiceImpl extends RemoteDataSourceServiceGrpc.RemoteDataSourceServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private final DelayStrategy delayStrategy;

    public RemoteDataSourceServiceImpl(DelayStrategy delayStrategy) {
        this.delayStrategy = delayStrategy;
    }

    @Override
    public void getData(Interval interval, StreamObserver<DataMessage> responseObserver) {
        for (var i = interval.getFirstValue(); i <= interval.getLastValue(); i++) {
            sleep();
            responseObserver.onNext(makeDataMessage(i));
        }
        responseObserver.onCompleted();
    }

    private DataMessage makeDataMessage(long i) {
        return DataMessage.newBuilder().setNumber(i).build();
    }

    private void sleep() {
        try {
            log.info("sleep {} mils", delayStrategy.getDelay());
            Thread.sleep(delayStrategy.getDelay());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

