package ru.otus.mappers.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.dto.requests.ContactsRequest;
import ru.otus.jpa.entities.Buildings;
import ru.otus.jpa.entities.Departments;
import ru.otus.jpa.entities.Placements;
import ru.otus.jpa.repositories.PlacementsRepository;
import ru.otus.mappers.entity.PlacementsEntityMapper;
import ru.otus.models.organization.ContactsModel;
import ru.otus.models.organization.PlacementsModel;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class ContactsRequestMapperTest {
    private static final Logger log = LoggerFactory.getLogger(ContactsRequestMapperTest.class);

    private final static String NAME = "name";
    private final static String PHONE = "phone";
    private final static String DESCRIPTION = "Description";

    private final static Placements PLACEMENTS = Placements.builder()
            .id(1L)
            .building(Buildings.builder().name(NAME).description(DESCRIPTION).build())
            .department(Departments.builder().id(1L).name(NAME).description(DESCRIPTION).build())
            .active(true).build();

    private ContactsRequestMapper contactsRequestMapper;

    @Autowired
    private PlacementsEntityMapper placementsEntityMapper;

    @BeforeEach
    void setUp() {
        var placementsRepository = mock(PlacementsRepository.class);

        when(placementsRepository.findById(1L)).thenReturn(Optional.ofNullable(PLACEMENTS));

        contactsRequestMapper = new ContactsRequestMapper(placementsRepository, placementsEntityMapper);
    }

    @Test
    void updateModel() {
        var contactsRequest = ContactsRequest.builder().active(true).build();
        var contactsModel = ContactsModel.builder()
                .id(1L)
                .placement(PlacementsModel.builder().id(1L).build())
                .active(false).build();
        contactsRequestMapper.updateModel(contactsModel, contactsRequest);
        assertEquals(1L, contactsModel.getId());
        assertEquals(1L, contactsModel.getPlacementId());
        assertTrue(contactsModel.isActive());

        contactsRequest = ContactsRequest.builder()
                .name(NAME)
                .phone(PHONE)
                .placementId(1L)
                .active(true)
                .build();
        contactsModel = ContactsModel.builder().build();
        contactsRequestMapper.updateModel(contactsModel, contactsRequest);
        assertEqualsContactsModelAndRequest(contactsModel, contactsRequest);
    }

    @Test
    void createModel() {
        var contactsRequest = ContactsRequest.builder().build();
        var contactsModel = contactsRequestMapper.createModel(contactsRequest);
        assertEqualsContactsModelAndRequest(contactsModel, contactsRequest);
        assertFalse(contactsModel.isActive());

        contactsRequest = ContactsRequest.builder()
                .name(NAME)
                .phone(PHONE)
                .placementId(1L)
                .active(true)
                .build();
        contactsModel = contactsRequestMapper.createModel(contactsRequest);
        assertEqualsContactsModelAndRequest(contactsModel, contactsRequest);
    }

    private void assertEqualsContactsModelAndRequest(ContactsModel model, ContactsRequest request) {
        assertEquals(model.getName(), request.getName());
        assertEquals(model.getPhone(), request.getPhone());
        assertEquals(model.getPlacementId(), request.getPlacementId());
        assertEquals(model.isActive(), request.isActive());
    }
}