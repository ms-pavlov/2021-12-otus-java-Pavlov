package ru.otus.mappers.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.dto.requests.PlacementsRequest;
import ru.otus.jpa.entities.Buildings;
import ru.otus.jpa.entities.Departments;
import ru.otus.jpa.repositories.BuildingsRepository;
import ru.otus.jpa.repositories.DepartmentsRepository;
import ru.otus.mappers.entity.BuildingsEntityMapper;
import ru.otus.mappers.entity.DepartmentsEntityMapper;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.models.organization.PlacementsModel;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class PlacementsRequestMapperTest {
    private static final Logger log = LoggerFactory.getLogger(PlacementsRequestMapperTest.class);

    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    private final static Departments DEPARTMENTS = Departments
            .builder()
            .id(1L)
            .name(NAME)
            .description(DESCRIPTION)
            .active(true)
            .build();

    private final static Buildings BUILDINGS = Buildings
            .builder()
            .id(2L)
            .name(NAME)
            .description(DESCRIPTION)
            .active(true)
            .build();

    private PlacementsRequestMapper placementsRequestMapper;

    @Autowired
    private BuildingsEntityMapper buildingsEntityMapper;
    @Autowired
    private DepartmentsEntityMapper departmentsEntityMapper;



    @BeforeEach
    void setUp() {
        BuildingsRepository buildingsRepository = mock(BuildingsRepository.class);
        DepartmentsRepository departmentsRepository = mock(DepartmentsRepository.class);

        when(buildingsRepository.findById(2L)).thenReturn(Optional.ofNullable(BUILDINGS));
        when(departmentsRepository.findById(1L)).thenReturn(Optional.ofNullable(DEPARTMENTS));

        this.placementsRequestMapper = new PlacementsRequestMapper(buildingsRepository, departmentsRepository, buildingsEntityMapper, departmentsEntityMapper);
    }

    @Test
    void updateModel() {
        var placementsRequest = PlacementsRequest.builder().active(true).build();
        var placementsModel = PlacementsModel.builder()
                .id(1L)
                .building(BuildingsModel.builder().id(2L).name(NAME).description(DESCRIPTION).build())
                .department(DepartmentsModel.builder().id(1L).name(NAME).description(DESCRIPTION).build())
                .active(false).build();
        placementsRequestMapper.updateModel(placementsModel, placementsRequest);
        assertEquals(1L, placementsModel.getId());
        assertEquals(2L, placementsModel.getBuildingId());
        assertEquals(1L, placementsModel.getDepartmentId());
        assertTrue(placementsModel.isActive());

        placementsRequest = PlacementsRequest.builder()
                .buildingId(2L)
                .departmentId(1L)
                .active(true)
                .build();
        placementsModel = PlacementsModel.builder().build();
        placementsRequestMapper.updateModel(placementsModel, placementsRequest);
        assertEqualsPlacementsModelAndRequest(placementsModel, placementsRequest);
    }

    @Test
    void createModel() {
        var placementsRequest = PlacementsRequest.builder().build();
        var placementsModel = placementsRequestMapper.createModel(placementsRequest);
        assertEqualsPlacementsModelAndRequest(placementsModel, placementsRequest);
        assertFalse(placementsModel.isActive());

        placementsRequest = PlacementsRequest.builder()
                .buildingId(2L)
                .departmentId(1L)
                .active(true)
                .build();
        placementsModel = placementsRequestMapper.createModel(placementsRequest);
        assertEqualsPlacementsModelAndRequest(placementsModel, placementsRequest);
    }

    private void assertEqualsPlacementsModelAndRequest(PlacementsModel model, PlacementsRequest request) {
        assertEquals(model.getBuildingId(), request.getBuildingId());
        assertEquals(model.getDepartmentId(), request.getDepartmentId());
        assertEquals(model.isActive(), request.isActive());
    }
}