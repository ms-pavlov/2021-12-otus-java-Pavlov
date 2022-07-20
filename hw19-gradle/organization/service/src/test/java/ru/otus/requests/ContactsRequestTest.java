package ru.otus.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.Validator;
import ru.otus.dto.requests.ContactsRequest;
import ru.otus.requests.utils.FieldValidatorCheckerImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.requests.utils.ValidationCheckUtils.getLongString;
import static ru.otus.requests.utils.ValidationCheckUtils.hasErrors;

@SpringBootTest
@ActiveProfiles("test")
class ContactsRequestTest {
    @Autowired
    private Validator getValidator;

    private ContactsRequest contacts;

    @BeforeEach
    void setUp() {
        contacts = ContactsRequest.builder().build();
    }

    @Test
    void checkContactsNameValidation() {
        contacts.setName("");
        assertTrue(hasErrors(contacts, new FieldValidatorCheckerImpl("name", getValidator)));

        contacts.setName(getLongString(255));
        assertTrue(hasErrors(contacts, new FieldValidatorCheckerImpl("name", getValidator)));

        contacts.setName("Simple Name");
        assertFalse(hasErrors(contacts, new FieldValidatorCheckerImpl("name", getValidator)));
    }

    @Test
    void checkContactsPhoneValidation() {
        contacts.setPhone(getLongString(255));
        assertTrue(hasErrors(contacts, new FieldValidatorCheckerImpl("phone", getValidator)));

        contacts.setPhone("Simple Phone");
        assertFalse(hasErrors(contacts, new FieldValidatorCheckerImpl("phone", getValidator)));
    }

    @Test
    void checkContactsPlacementIdValidation() {
        contacts.setPlacementId(null);
        assertTrue(hasErrors(contacts, new FieldValidatorCheckerImpl("placementId", getValidator)));

        contacts.setPlacementId(0L);
        assertTrue(hasErrors(contacts, new FieldValidatorCheckerImpl("placementId", getValidator)));

        contacts.setPlacementId(10L);
        assertFalse(hasErrors(contacts, new FieldValidatorCheckerImpl("placementId", getValidator)));
    }

}