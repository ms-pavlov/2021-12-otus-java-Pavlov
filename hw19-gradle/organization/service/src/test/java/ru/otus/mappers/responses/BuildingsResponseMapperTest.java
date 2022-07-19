package ru.otus.mappers.responses;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.dto.responses.BuildingsResponse;
import ru.otus.models.organization.BuildingsModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class BuildingsResponseMapperTest {
    private static final Logger log = LoggerFactory.getLogger(BuildingsResponseMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    @Autowired
    private BuildingsResponseMapper buildingsResponseMapper;

    @Test
    void toResponse() {
        var buildingsModel = BuildingsModel.builder().build();
        var buildingsResponse= buildingsResponseMapper.toResponse(buildingsModel);
        assertEqualsBuildingsModelAndResponse(buildingsModel, buildingsResponse);

        buildingsModel = BuildingsModel.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        buildingsResponse = buildingsResponseMapper.toResponse(buildingsModel);
        assertEqualsBuildingsModelAndResponse(buildingsModel, buildingsResponse);
    }

    private void assertEqualsBuildingsModelAndResponse(BuildingsModel model, BuildingsResponse response) {
        assertEquals(model.getId(), response.getId());
        assertEquals(model.getName(), response.getName());
        assertEquals(model.getDescription(), response.getDescription());
        assertEquals(model.isActive(), response.isActive());
    }
}