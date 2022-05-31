package ru.otus.server;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.client.NumberObserver;
import ru.otus.protobuf.generated.DataMessage;
import ru.otus.protobuf.generated.Interval;
import ru.otus.protobuf.generated.RemoteDataSourceServiceGrpc;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RemoteDataSourceTest {
    private static final Logger log = LoggerFactory.getLogger(RemoteDataSourceTest.class);
    private static final long FIRST = 1;
    private static final long LAST = 3;
    private static final String NAME = "test";

    private Server server;
    private RemoteDataSourceServiceGrpc.RemoteDataSourceServiceStub asyncStub;
    private CountDownLatch latch;
    private NumberObserver observer;

    @BeforeEach
    void setUp() throws IOException {
        server = InProcessServerBuilder
                .forName(NAME)
                .directExecutor()
                .addService(new RemoteDataSourceServiceImpl(() -> 0))
                .build()
                .start();

        ManagedChannel channel = InProcessChannelBuilder
                .forName(NAME)
                .directExecutor()
                .usePlaintext()
                .build();

        asyncStub = RemoteDataSourceServiceGrpc.newStub(channel);

        latch = new CountDownLatch(1);
        observer = spy(new NumberObserver(latch::countDown));
    }

    @AfterEach
    void stop() {
        if (server != null) {
            server.shutdown();
        }
    }


    @Test
    void getData() throws InterruptedException {
        asyncStub.getData(Interval.newBuilder().setFirstValue(FIRST).setLastValue(LAST).build(), observer);
        latch.await();

        for (var i = FIRST; i <= LAST; i++) {
            verify(observer, times(1)).onNext(DataMessage.newBuilder().setNumber(i).build());
        }

        assertEquals(3L, observer.extractCurrentNumber());
    }
}