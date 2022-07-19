package ru.otus.services.environment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class RoomsEnvironmentTest {
    @Autowired
    private BuildingsEnvironment environment;

    @Test
    void existInSpring() {
        assertNotNull(environment);
        assertNotNull(environment.getRepository());
        assertNotNull(environment.getValidator());
        assertNotNull(environment.getResponseMapper());
        assertNotNull(environment.getRequestMapper());
        assertNotNull(environment.getEntityMapper());
    }
}