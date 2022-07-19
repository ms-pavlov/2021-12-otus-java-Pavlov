package ru.otus.mappers.entity;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.jpa.entities.Contacts;
import ru.otus.models.organization.ContactsModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class ContactsEntityMapperTest {
    private static final Logger log = LoggerFactory.getLogger(ContactsEntityMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String PHONE = "SamePhone";


    @Autowired
    private ContactsEntityMapper contactsEntityMapper;

    @Test
    void checkToEntityMapping() {
        var model = ContactsModel.builder().build();
        var entity = contactsEntityMapper.toEntity(model);
        assertEqualsContactsModelAndEntity(model, entity);

        model = ContactsModel.builder()
                .id(ID)
                .name(NAME)
                .phone(PHONE)
                .active(true)
                .build();
        entity = contactsEntityMapper.toEntity(model);
        assertEqualsContactsModelAndEntity(model, entity);
    }

    @Test
    void checkToModelMapping() {
        var entity = Contacts.builder().build();
        var model = contactsEntityMapper.toModel(entity);
        assertEqualsContactsModelAndEntity(model, entity);

        entity = Contacts.builder()
                .id(ID)
                .name(NAME)
                .phone(PHONE)
                .active(true)
                .build();
        model = contactsEntityMapper.toModel(entity);
        assertEqualsContactsModelAndEntity(model, entity);
    }

    public static void assertEqualsContactsModelAndEntity(ContactsModel model, Contacts entity) {
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.isActive(), entity.isActive());
        assertEquals(model.getDepartmentId(), entity.getDepartmentId());
        assertEquals(model.getDepartmentName(), entity.getDepartmentName());
    }
}