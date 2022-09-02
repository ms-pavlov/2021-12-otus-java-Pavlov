package ru.otus.services.environment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class PlacementsEnvironmentTest {
    @Autowired
    private PlacementsEnvironment environment;

    @Test
    void existInSpring() {
        assertNotNull(environment);
        assertNotNull(environment.getAfterModifyProcessor());
        assertNotNull(environment.getDataSource());
        assertNotNull(environment.getDeleteMarker());
        assertNotNull(environment.getRequestStrategy());
    }
}