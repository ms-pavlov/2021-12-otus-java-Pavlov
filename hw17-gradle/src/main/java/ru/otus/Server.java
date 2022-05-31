package ru.otus;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.server.RemoteDataSourceServiceImpl;

import java.io.IOException;

public class Server {
    private static final int INTERVAL = 2000;
    private static final int SERVER_PORT = 8190;

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String... args) throws IOException, InterruptedException {
        var dataSourceService = new RemoteDataSourceServiceImpl(() -> INTERVAL);

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(dataSourceService).build();
        server.start();

        log.info("server waiting for client connections...");
        server.awaitTermination();
    }
}
