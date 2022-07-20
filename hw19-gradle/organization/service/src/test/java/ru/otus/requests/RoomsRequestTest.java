package ru.otus.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.RoomsRequest;
import ru.otus.requests.utils.FieldValidatorCheckerImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.requests.utils.ValidationCheckUtils.getLongString;
import static ru.otus.requests.utils.ValidationCheckUtils.hasErrors;

@SpringBootTest
@ActiveProfiles("test")
class RoomsRequestTest {
    @Autowired
    private Validator getValidator;

    private RoomsRequest rooms;

    @BeforeEach
    void setUp() {
        rooms = RoomsRequest.builder().build();
    }

    @Test
    void checkRoomsNameValidation() {
        rooms.setName("");
        assertTrue(hasErrors(rooms, new FieldValidatorCheckerImpl("name", getValidator)));

        rooms.setName(getLongString(255));
        assertTrue(hasErrors(rooms, new FieldValidatorCheckerImpl("name", getValidator)));

        rooms.setName("Simple Name");
        assertFalse(hasErrors(rooms, new FieldValidatorCheckerImpl("name", getValidator)));
    }

    @Test
    void checkRoomsDescriptionValidation() {
        rooms.setDescription(getLongString(1000));
        assertTrue(hasErrors(rooms, new FieldValidatorCheckerImpl("description", getValidator)));

        rooms.setDescription("Simple Description");
        assertFalse(hasErrors(rooms, new FieldValidatorCheckerImpl("description", getValidator)));
    }

    @Test
    void checkRoomsPlacementIdValidation() {
        rooms.setPlacementId(null);
        assertTrue(hasErrors(rooms, new FieldValidatorCheckerImpl("placementId", getValidator)));

        rooms.setPlacementId(0L);
        assertTrue(hasErrors(rooms, new FieldValidatorCheckerImpl("placementId", getValidator)));

        rooms.setPlacementId(10L);
        assertFalse(hasErrors(rooms, new FieldValidatorCheckerImpl("placementId", getValidator)));
    }
}