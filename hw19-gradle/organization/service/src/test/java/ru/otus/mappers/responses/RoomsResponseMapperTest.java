package ru.otus.mappers.responses;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.dto.responses.RoomsResponse;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.models.organization.RoomsModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class RoomsResponseMapperTest {
    private static final Logger log = LoggerFactory.getLogger(RoomsResponseMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    @Autowired
    private RoomsResponseMapper roomsResponseMapper;

    @Test
    void toResponse() {
        var roomsModel = RoomsModel.builder().build();
        var roomsResponse = roomsResponseMapper.toResponse(roomsModel);
        assertEqualsRoomsModelAndResponse(roomsModel, roomsResponse);

        roomsModel = RoomsModel.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        roomsModel.setPlacement(PlacementsModel.builder().id(ID).build());
        roomsResponse = roomsResponseMapper.toResponse(roomsModel);
        assertEqualsRoomsModelAndResponse(roomsModel, roomsResponse);
    }

    public void assertEqualsRoomsModelAndResponse(RoomsModel model, RoomsResponse response) {
        assertEquals(model.getId(), response.getId());
        assertEquals(model.getName(), response.getName());
        assertEquals(model.getDescription(), response.getDescription());
        assertEquals(model.isActive(), response.isActive());
        assertEquals(model.getPlacementId(), response.getPlacementId());
    }
}