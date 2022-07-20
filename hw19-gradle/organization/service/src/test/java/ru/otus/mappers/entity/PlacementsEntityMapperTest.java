package ru.otus.mappers.entity;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.jpa.entities.*;
import ru.otus.models.organization.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.otus.mappers.entity.ContactsEntityMapperTest.assertEqualsContactsModelAndEntity;
import static ru.otus.mappers.entity.RoomsEntityMapperTest.assertEqualsRoomsModelAndEntity;

@SpringBootTest
@ActiveProfiles("test")
class PlacementsEntityMapperTest {
    private static final Logger log = LoggerFactory.getLogger(PlacementsEntityMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String PHONE = "phone";
    private final static String DESCRIPTION = "description";
    private final static Buildings BUILDINGS = Buildings.builder()
            .id(1L).name(NAME).description(DESCRIPTION).build();
    private final static Departments DEPARTMENTS = Departments.builder()
            .id(1L).name(NAME).description(DESCRIPTION).build();
    private final static BuildingsModel BUILDINGS_MODEL = BuildingsModel.builder()
            .id(1L).name(NAME).description(DESCRIPTION).build();
    private final static DepartmentsModel DEPARTMENTS_MODEL = DepartmentsModel.builder()
            .id(1L).name(NAME).description(DESCRIPTION).build();


    @Autowired
    private PlacementsEntityMapper placementsEntityMapper;

    @Test
    void checkToEntityMapping() {
        var model = PlacementsModel.builder().build();
        var entity = placementsEntityMapper.toEntity(model);
        assertEqualsPlacementsModelAndEntity(model, entity);

        model = PlacementsModel.builder()
                .id(ID)
                .building(BUILDINGS_MODEL)
                .department(DEPARTMENTS_MODEL)
                .active(true)
                .build();
        entity = placementsEntityMapper.toEntity(model);
        assertEqualsPlacementsModelAndEntity(model, entity);
    }

    @Test
    void checkToModelMapping() {
        var entity = Placements.builder().build();
        var model = placementsEntityMapper.toModel(entity);
        assertEqualsPlacementsModelAndEntity(model, entity);

        entity = Placements.builder()
                .id(ID)
                .department(DEPARTMENTS)
                .building(BUILDINGS)
                .active(true)
                .build();
        model = placementsEntityMapper.toModel(entity);
        log.info("entity {}", entity);
        log.info("model {}", model);
        assertEqualsPlacementsModelAndEntity(model, entity);
    }

    @Test
    void checkContactsMappingToModel() {
        var placements = Placements.builder()
                .id(ID).build();
        List<Contacts> contacts = List.of(Contacts.builder()
                .id(ID).name(NAME).phone(PHONE).build());
        placements.setContacts(contacts);

        var model = placementsEntityMapper.toModel(placements);

        assertNotNull(model.getContacts());
        assertEquals(contacts.size(), model.getContacts().size());

        var contactsModels = new ArrayList<>(model.getContacts());
        for (var i = 0; i < placements.getContacts().size(); i++) {
            assertEqualsContactsModelAndEntity(contactsModels.get(i), contacts.get(i));
        }
    }

    @Test
    void checkContactsMappingToEntity() {
        var placementsModel = PlacementsModel.builder()
                .id(ID).build();
        List<ContactsModel> contactsModels = List.of(ContactsModel.builder()
                .id(ID).name(NAME).phone(PHONE).build());
        placementsModel.setContacts(contactsModels);

        var entity = placementsEntityMapper.toEntity(placementsModel);

        assertNotNull(entity.getContacts());
        assertEquals(contactsModels.size(), entity.getContacts().size());

        var contacts = new ArrayList<>(entity.getContacts());
        for (var i = 0; i < placementsModel.getContacts().size(); i++) {
            assertEqualsContactsModelAndEntity(contactsModels.get(i), contacts.get(i));
        }
    }

    @Test
    void checkRoomsMappingToModel() {
        var placements = Placements.builder()
                .id(ID).build();
        List<Rooms> rooms = List.of(Rooms.builder()
                .id(ID).name(NAME).description(PHONE).build());
        placements.setRooms(rooms);

        var model = placementsEntityMapper.toModel(placements);

        assertNotNull(model.getRooms());
        assertEquals(rooms.size(), model.getRooms().size());

        var roomsModels = new ArrayList<>(model.getRooms());
        for (var i = 0; i < placements.getRooms().size(); i++) {
            assertEqualsRoomsModelAndEntity(roomsModels.get(i), rooms.get(i));
        }
    }

    @Test
    void checkRoomsMappingToEntity() {
        var placementsModel = PlacementsModel.builder()
                .id(ID).build();
        List<RoomsModel> roomsModels = List.of(RoomsModel.builder()
                .id(ID).name(NAME).description(DESCRIPTION).build());
        placementsModel.setRooms(roomsModels);

        var entity = placementsEntityMapper.toEntity(placementsModel);

        assertNotNull(entity.getRooms());
        assertEquals(roomsModels.size(), entity.getRooms().size());

        var rooms = new ArrayList<>(entity.getRooms());
        for (var i = 0; i < placementsModel.getRooms().size(); i++) {
            assertEqualsRoomsModelAndEntity(roomsModels.get(i), rooms.get(i));
        }
    }

    public static void assertEqualsPlacementsModelAndEntity(PlacementsModel model, Placements entity) {
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.isActive(), entity.isActive());

        assertEquals(model.getDepartmentId(), entity.getDepartmentId());
        assertEquals(model.getDepartmentName(), entity.getDepartmentName());

        assertEquals(model.getBuildingId(), entity.getBuildingId());
        assertEquals(model.getBuildingName(), entity.getBuildingName());

    }
}