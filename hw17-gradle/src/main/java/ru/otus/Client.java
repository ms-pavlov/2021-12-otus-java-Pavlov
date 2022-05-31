package ru.otus;

import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.client.NumberObserver;
import ru.otus.protobuf.generated.Interval;
import ru.otus.protobuf.generated.RemoteDataSourceServiceGrpc;

import java.util.concurrent.CountDownLatch;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int FIRST_VALUR = 15;
    private static final int LAST_VALUR = 45;
    private static final int COUNT = 50;
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static void main(String... args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var latch = new CountDownLatch(1);
        var observer = new NumberObserver(latch::countDown);

        var stub = RemoteDataSourceServiceGrpc.newStub(channel);
        stub.getData(makeInterval(FIRST_VALUR, LAST_VALUR), observer);
        log.info("numbers Client is starting...");

        long currentValue = 0;
        for (var i = 0; i<=COUNT; i++) {
            sleep();
            currentValue = currentValue + observer.extractCurrentNumber() + 1;
            log.info("currentValue: {}", currentValue);
        }

        latch.await();
        channel.shutdown();
    }

    private static Interval makeInterval(long first, long last) {
        return Interval.newBuilder().setFirstValue(first).setLastValue(last).build();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
