package ru.otus.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.requests.utils.FieldValidatorCheckerImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.requests.utils.ValidationCheckUtils.getLongString;
import static ru.otus.requests.utils.ValidationCheckUtils.hasErrors;

@SpringBootTest
@ActiveProfiles("test")
class DepartmentsRequestTest {
    @Autowired
    private Validator getValidator;

    private DepartmentsRequest department;

    @BeforeEach
    void setUp() {
        department = DepartmentsRequest.builder().build();
    }

    @Test
    void checkDepartmentNameValidation() {

        department.setName("");
        assertTrue(hasErrors(department, new FieldValidatorCheckerImpl("name", getValidator)));

        department.setName(getLongString(255));
        assertTrue(hasErrors(department, new FieldValidatorCheckerImpl("name", getValidator)));

        department.setName("Vasya");
        assertFalse(hasErrors(department, new FieldValidatorCheckerImpl("name", getValidator)));
    }

    @Test
    void checkDepartmentDescriptionValidation() {
        department.setDescription(getLongString(1000));
        assertTrue(hasErrors(department, new FieldValidatorCheckerImpl("description", getValidator)));

        department.setDescription("");
        assertFalse(hasErrors(department, new FieldValidatorCheckerImpl("description", getValidator)));
    }
}