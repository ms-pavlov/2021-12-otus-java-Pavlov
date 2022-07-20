package ru.otus.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.PlacementsRequest;
import ru.otus.requests.utils.FieldValidatorCheckerImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.requests.utils.ValidationCheckUtils.hasErrors;

@SpringBootTest
@ActiveProfiles("test")
class PlacementsRequestTest {
    @Autowired
    private Validator getValidator;

    private PlacementsRequest placement;

    @BeforeEach
    void setUp() {
        placement = PlacementsRequest.builder().build();
    }

    @Test
    void checkPlacementsDepartmentIdValidation() {
        placement.setDepartmentId(null);
        assertTrue(hasErrors(placement, new FieldValidatorCheckerImpl("departmentId", getValidator)));

        placement.setDepartmentId(0L);
        assertTrue(hasErrors(placement, new FieldValidatorCheckerImpl("departmentId", getValidator)));

        placement.setDepartmentId(10L);
        assertFalse(hasErrors(placement, new FieldValidatorCheckerImpl("departmentId", getValidator)));
    }

    @Test
    void checkPlacementsBuildingIdValidation() {
        placement.setBuildingId(null);
        assertTrue(hasErrors(placement, new FieldValidatorCheckerImpl("buildingId", getValidator)));

        placement.setBuildingId(0L);
        assertTrue(hasErrors(placement, new FieldValidatorCheckerImpl("buildingId", getValidator)));

        placement.setBuildingId(10L);
        assertFalse(hasErrors(placement, new FieldValidatorCheckerImpl("buildingId", getValidator)));
    }
}