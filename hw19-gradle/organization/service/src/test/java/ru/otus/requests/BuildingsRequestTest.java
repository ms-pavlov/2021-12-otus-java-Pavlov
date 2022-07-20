package ru.otus.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.requests.utils.FieldValidatorCheckerImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.requests.utils.ValidationCheckUtils.getLongString;
import static ru.otus.requests.utils.ValidationCheckUtils.hasErrors;

@SpringBootTest
@ActiveProfiles("test")
class BuildingsRequestTest {
    @Autowired
    private Validator getValidator;

    private BuildingsRequest buildings;

    @BeforeEach
    void setUp() {
        buildings = BuildingsRequest.builder().build();
    }

    @Test
    void checkBuildingsNameValidation() {
        buildings.setName("");
        assertTrue(hasErrors(buildings, new FieldValidatorCheckerImpl("name", getValidator)));

        buildings.setName(getLongString(255));
        assertTrue(hasErrors(buildings, new FieldValidatorCheckerImpl("name", getValidator)));

        buildings.setName("Simple Name");
        assertFalse(hasErrors(buildings, new FieldValidatorCheckerImpl("name", getValidator)));
    }

    @Test
    void checkBuildingsDescriptionValidation() {
        buildings.setDescription(getLongString(1000));
        assertTrue(hasErrors(buildings, new FieldValidatorCheckerImpl("description", getValidator)));

        buildings.setDescription("Simple Description");
        assertFalse(hasErrors(buildings, new FieldValidatorCheckerImpl("description", getValidator)));
    }

}