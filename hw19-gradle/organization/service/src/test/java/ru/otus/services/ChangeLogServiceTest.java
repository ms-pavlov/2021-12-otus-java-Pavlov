package ru.otus.services;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.scheduler.Scheduler;
import ru.otus.messages.PlacementsMessage;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.models.organization.RoomsModel;
import ru.otus.services.rest.PlacementsMessagesQueue;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ChangeLogServiceTest {
    private static final Logger log = LoggerFactory.getLogger(ChangeLogServiceTest.class);
    private static final int COUNT = 10;
    private final static PlacementsMessage MESSAGE = new PlacementsMessage(PlacementsModel.builder()
            .active(true)
            .building(BuildingsModel.builder().name("BuildingsName").build())
            .department(DepartmentsModel.builder().name("DepartmentsName").build())
            .rooms(List.of(RoomsModel.builder().name("RoomsName").build()))
            .build());

    private final ChangeLogService service;

    @Autowired
    ChangeLogServiceTest(Scheduler timer) {
        this.service = new ChangeLogService(new PlacementsMessagesQueue(timer));
    }

    @Test
    void send() {
        var flux = service.getFlux();

        service.send(MESSAGE);

        assertNotNull(flux);

        var result = flux.blockFirst();
        log.info("message send {}", result);

        assertEquals(MESSAGE, result);
    }

    @Test
    void sendFew() {
        var flux = service.getFlux();
        for (var i = 0; i < COUNT; i++) {
            service.send(MESSAGE);
        }

        assertNotNull(flux);

        var result = flux.buffer(COUNT).blockFirst();
        assertNotNull(result);
        assertEquals(COUNT, result.size());
        for (var message : result) {
            assertEquals(MESSAGE, message);
        }

    }

}