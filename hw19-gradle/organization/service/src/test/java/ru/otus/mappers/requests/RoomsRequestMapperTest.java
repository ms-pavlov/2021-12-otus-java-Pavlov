package ru.otus.mappers.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.dto.requests.RoomsRequest;
import ru.otus.jpa.entities.Buildings;
import ru.otus.jpa.entities.Departments;
import ru.otus.jpa.entities.Placements;
import ru.otus.jpa.repositories.PlacementsRepository;
import ru.otus.mappers.entity.PlacementsEntityMapper;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.models.organization.RoomsModel;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class RoomsRequestMapperTest {
    private static final Logger log = LoggerFactory.getLogger(RoomsRequestMapperTest.class);

    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    private final static Placements PLACEMENTS = Placements.builder()
            .id(1L)
            .building(Buildings.builder().name(NAME).description(DESCRIPTION).build())
            .department(Departments.builder().id(1L).name(NAME).description(DESCRIPTION).build())
            .active(true).build();

    private RoomsRequestMapper roomsRequestMapper;

    @Autowired
    private PlacementsEntityMapper placementsEntityMapper;

    @BeforeEach
    void setUp() {
        var placementsRepository = mock(PlacementsRepository.class);

        when(placementsRepository.findById(1L)).thenReturn(Optional.ofNullable(PLACEMENTS));

        roomsRequestMapper = new RoomsRequestMapper(placementsRepository, placementsEntityMapper);
    }

    @Test
    void updateModel() {
        var roomsRequest = RoomsRequest.builder().active(true).build();
        var roomsModel = RoomsModel.builder()
                .id(1L)
                .placement(PlacementsModel.builder().id(1L).build())
                .active(false).build();
        roomsRequestMapper.updateModel(roomsModel, roomsRequest);
        assertEquals(1L, roomsModel.getId());
        assertEquals(1L, roomsModel.getPlacementId());
        assertTrue(roomsModel.isActive());

        roomsRequest = RoomsRequest.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .placementId(1L)
                .active(true)
                .build();
        roomsModel = RoomsModel.builder().build();
        roomsRequestMapper.updateModel(roomsModel, roomsRequest);
        assertEqualsRoomsModelAndRequest(roomsModel, roomsRequest);
    }

    @Test
    void createModel() {
        var roomsRequest = RoomsRequest.builder().build();
        var roomsModel = roomsRequestMapper.createModel(roomsRequest);
        assertEqualsRoomsModelAndRequest(roomsModel, roomsRequest);
        assertFalse(roomsModel.isActive());

        roomsRequest = RoomsRequest.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .placementId(1L)
                .active(true)
                .build();
        roomsModel = roomsRequestMapper.createModel(roomsRequest);
        assertEqualsRoomsModelAndRequest(roomsModel, roomsRequest);
    }

    private void assertEqualsRoomsModelAndRequest(RoomsModel model, RoomsRequest request) {
        assertEquals(model.getName(), request.getName());
        assertEquals(model.getDescription(), request.getDescription());
        assertEquals(model.getPlacementId(), request.getPlacementId());
        assertEquals(model.isActive(), request.isActive());
    }
}