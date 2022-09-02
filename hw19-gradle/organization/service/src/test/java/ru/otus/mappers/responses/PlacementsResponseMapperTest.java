package ru.otus.mappers.responses;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.dto.responses.PlacementsResponse;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.models.organization.PlacementsModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class PlacementsResponseMapperTest {
    private static final Logger log = LoggerFactory.getLogger(PlacementsResponseMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    @Autowired
    private PlacementsResponseMapper placementsResponseMapper;

    @Test
    void toResponse() {
        var placementsModel = PlacementsModel.builder().build();
        var placementsResponse = placementsResponseMapper.toResponse(placementsModel);
        assertEqualsDepartmentsModelAndResponse(placementsModel, placementsResponse);

        placementsModel = PlacementsModel.builder()
                .id(ID)
                .active(true)
                .building(BuildingsModel.builder().name(NAME).description(DESCRIPTION).build())
                .department(DepartmentsModel.builder().name(NAME).description(DESCRIPTION).build())
                .build();
        placementsResponse = placementsResponseMapper.toResponse(placementsModel);
        log.info(placementsResponse.getPlacementsName());
        assertEqualsDepartmentsModelAndResponse(placementsModel, placementsResponse);
    }

    public static void assertEqualsDepartmentsModelAndResponse(PlacementsModel model, PlacementsResponse response) {
        assertEquals(model.getId(), response.getId());
        assertEquals(model.isActive(), response.isActive());
        assertEquals(model.getPlacementsName(), response.getPlacementsName());
        assertEquals(model.getBuildingId(), response.getBuildingId());
        assertEquals(model.getDepartmentId(), response.getDepartmentId());
    }
}