package ru.otus.mappers.requests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.models.organization.DepartmentsModel;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DepartmentsRequestMapperTest {
    private static final Logger log = LoggerFactory.getLogger(DepartmentsRequestMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    @Autowired
    private DepartmentsRequestMapper departmentsRequestMapper;

    @Test
    void updateModel() {
        var departmentsRequest = DepartmentsRequest.builder().active(true).build();
        var departmentsModel = DepartmentsModel.builder().id(ID).name(NAME).description(DESCRIPTION).active(false).build();
        departmentsRequestMapper.updateModel(departmentsModel, departmentsRequest);
        assertEquals(ID, departmentsModel.getId());
        assertEquals(NAME, departmentsModel.getName());
        assertEquals(DESCRIPTION, departmentsModel.getDescription());
        assertTrue(departmentsModel.isActive());

        departmentsRequest = DepartmentsRequest.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        departmentsModel = DepartmentsModel.builder().build();
        departmentsRequestMapper.updateModel(departmentsModel, departmentsRequest);
        assertEqualsDepartmentsModelAndRequest(departmentsModel, departmentsRequest);


    }

    @Test
    void createModel() {
        var departmentsRequest = DepartmentsRequest.builder().build();
        var departmentsModel = departmentsRequestMapper.createModel(departmentsRequest);
        assertEqualsDepartmentsModelAndRequest(departmentsModel, departmentsRequest);
        assertFalse(departmentsModel.isActive());

        departmentsRequest = DepartmentsRequest.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        departmentsModel = departmentsRequestMapper.createModel(departmentsRequest);
        assertEqualsDepartmentsModelAndRequest(departmentsModel, departmentsRequest);
    }

    private void assertEqualsDepartmentsModelAndRequest(DepartmentsModel model, DepartmentsRequest request) {
        assertEquals(model.getName(), request.getName());
        assertEquals(model.getDescription(), request.getDescription());
        assertEquals(model.isActive(), request.isActive());
    }
}