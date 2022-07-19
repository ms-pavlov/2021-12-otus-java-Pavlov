package ru.otus.mappers.requests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.models.organization.BuildingsModel;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BuildingsRequestMapperTest {
    private static final Logger log = LoggerFactory.getLogger(BuildingsRequestMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    @Autowired
    private BuildingsRequestMapper buildingsRequestMapper;

    @Test
    void updateModel() {
        var buildingsRequest = BuildingsRequest.builder().active(true).build();
        var buildingsModel = BuildingsModel.builder().id(ID).name(NAME).description(DESCRIPTION).active(false).build();
        buildingsRequestMapper.updateModel(buildingsModel, buildingsRequest);
        assertEquals(ID, buildingsModel.getId());
        assertEquals(NAME, buildingsModel.getName());
        assertEquals(DESCRIPTION, buildingsModel.getDescription());
        assertTrue(buildingsModel.isActive());

        buildingsRequest = BuildingsRequest.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .build();
        buildingsModel = BuildingsModel.builder().build();
        buildingsRequestMapper.updateModel(buildingsModel, buildingsRequest);
        assertEqualsBuildingsModelAndRequest(buildingsModel, buildingsRequest);
    }

    @Test
    void createModel() {
        var buildingsRequest = BuildingsRequest.builder().build();
        var buildingsModel = buildingsRequestMapper.createModel(buildingsRequest);
        assertEqualsBuildingsModelAndRequest(buildingsModel, buildingsRequest);
        assertFalse(buildingsModel.isActive());

        buildingsRequest = BuildingsRequest.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        buildingsModel = buildingsRequestMapper.createModel(buildingsRequest);
        assertEqualsBuildingsModelAndRequest(buildingsModel, buildingsRequest);
    }

    private void assertEqualsBuildingsModelAndRequest(BuildingsModel model, BuildingsRequest request) {
        assertEquals(model.getName(), request.getName());
        assertEquals(model.getDescription(), request.getDescription());
        assertEquals(model.isActive(), request.isActive());
    }
}