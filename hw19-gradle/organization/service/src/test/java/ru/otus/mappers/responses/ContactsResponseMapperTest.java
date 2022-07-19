package ru.otus.mappers.responses;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.dto.responses.ContactsResponse;
import ru.otus.models.organization.ContactsModel;
import ru.otus.models.organization.PlacementsModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class ContactsResponseMapperTest {
    private static final Logger log = LoggerFactory.getLogger(ContactsResponseMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    @Autowired
    private ContactsResponseMapper contactsResponseMapper;

    @Test
    void toResponse() {
        var contactsModel = ContactsModel.builder().build();
        var contactsResponse= contactsResponseMapper.toResponse(contactsModel);
        assertEqualsContactsModelAndResponse(contactsModel, contactsResponse);

        contactsModel = ContactsModel.builder()
                .id(ID)
                .name(NAME)
                .phone(DESCRIPTION)
                .active(true)
                .build();
        contactsModel.setPlacement(PlacementsModel.builder().id(ID).build());
        contactsResponse = contactsResponseMapper.toResponse(contactsModel);
        assertEqualsContactsModelAndResponse(contactsModel, contactsResponse);
    }

    public void assertEqualsContactsModelAndResponse(ContactsModel model, ContactsResponse response) {
        assertEquals(model.getId(), response.getId());
        assertEquals(model.getName(), response.getName());
        assertEquals(model.getPhone(), response.getPhone());
        assertEquals(model.isActive(), response.isActive());
        assertEquals(model.getPlacementId(), response.getPlacementId());
    }
}