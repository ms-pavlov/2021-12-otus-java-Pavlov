package ru.otus.controllers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.otus.controllers.rest.ChangeLogController;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.models.organization.RoomsModel;
import ru.otus.queue.MessagesQueue;
import ru.otus.services.ChangeLogService;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ChangeLogControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogControllerTest.class);

    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";
    private final static BuildingsRequest BUILDINGS_REQUEST = BuildingsRequest.builder()
            .name(NAME)
            .description(DESCRIPTION)
            .active(true)
            .build();
    private final static DepartmentsRequest DEPARTMENTS_REQUEST = DepartmentsRequest.builder()
            .name(NAME)
            .description(DESCRIPTION)
            .active(true)
            .build();

    private static final int COUNT = 10;
    private final static PlacementsMessage MESSAGE = new PlacementsMessage(PlacementsModel.builder()
            .active(true)
            .building(BuildingsModel.builder().name("BuildingsName").build())
            .department(DepartmentsModel.builder().name("DepartmentsName").build())
            .rooms(List.of(RoomsModel.builder().name("RoomsName").build()))
            .build());


    private final ChangeLogController changeLogController;

    private final WebTestClient webTestClient;
    private final ChangeLogService service;
    @Autowired
    ChangeLogControllerTest(ChangeLogController changeLogController, WebTestClient webTestClient, MessagesQueue<PlacementsMessage> messagesQueue) {
        this.changeLogController = changeLogController;

        this.service = new ChangeLogService(messagesQueue);
        this.webTestClient = webTestClient;
    }

    @Test
    void getChangeLog() {
        var changeLog = changeLogController.getChangeLog();

        service.send(MESSAGE);

        var message = changeLog.blockFirst();

        assertNotNull(message);
        assertEquals(message.getName(), MESSAGE.getName());

        log.info("message {}", message);

    }

    @Test
    void getWebChangeLog() throws InterruptedException {
        var adder = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                service.send(MESSAGE);
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        adder.start();

        var result = webTestClient.get()
                .uri("/api/changes/")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(PlacementsMessage.class)
                .getResponseBody()
                .take(1)
                .blockFirst();

        assertNotNull(result);
        assertEquals(MESSAGE.getName(), result.getName());

        adder.join();

    }
}