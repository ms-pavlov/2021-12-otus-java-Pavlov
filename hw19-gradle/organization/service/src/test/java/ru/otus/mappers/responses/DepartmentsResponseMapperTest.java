package ru.otus.mappers.responses;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.dto.responses.DepartmentsResponse;
import ru.otus.models.organization.DepartmentsModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class DepartmentsResponseMapperTest {
    private static final Logger log = LoggerFactory.getLogger(DepartmentsResponseMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    @Autowired
    private DepartmentsResponseMapper departmentsResponseMapper;

    @Test
    void toResponse() {
        var departmentsModel = DepartmentsModel.builder().build();
        var departmentsResponse = departmentsResponseMapper.toResponse(departmentsModel);
        assertEqualsDepartmentsModelAndResponse(departmentsModel, departmentsResponse);

        departmentsModel = DepartmentsModel.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        departmentsResponse = departmentsResponseMapper.toResponse(departmentsModel);
        assertEqualsDepartmentsModelAndResponse(departmentsModel, departmentsResponse);
    }



    private void assertEqualsDepartmentsModelAndResponse(DepartmentsModel model, DepartmentsResponse response) {
        assertEquals(model.getId(), response.getId());
        assertEquals(model.getName(), response.getName());
        assertEquals(model.getDescription(), response.getDescription());
        assertEquals(model.isActive(), response.isActive());
    }
}