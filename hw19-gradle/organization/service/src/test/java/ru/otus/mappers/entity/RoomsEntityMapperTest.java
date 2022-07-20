package ru.otus.mappers.entity;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.jpa.entities.Rooms;
import ru.otus.models.organization.RoomsModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class RoomsEntityMapperTest {
    private static final Logger log = LoggerFactory.getLogger(BuildingsEntityMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    @Autowired
    private RoomsEntityMapper roomsEntityMapper;

    @Test
    void checkToEntityMapping() {
        var model = RoomsModel.builder().build();
        log.info("model: {}", model);
        log.info("roomsEntityMapper: {}", roomsEntityMapper);
        var entity = roomsEntityMapper.toEntity(model);
        assertEqualsRoomsModelAndEntity(model, entity);

        model = RoomsModel.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        entity = roomsEntityMapper.toEntity(model);
        assertEqualsRoomsModelAndEntity(model, entity);
    }

    @Test
    void checkToModelMapping() {
        var entity = Rooms.builder().build();
        var model = roomsEntityMapper.toModel(entity);
        assertEqualsRoomsModelAndEntity(model, entity);

        entity = Rooms.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        model = roomsEntityMapper.toModel(entity);
        assertEqualsRoomsModelAndEntity(model, entity);
    }

    public static void assertEqualsRoomsModelAndEntity(RoomsModel model, Rooms entity) {
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getDescription(), entity.getDescription());
        assertEquals(model.isActive(), entity.isActive());
    }
}