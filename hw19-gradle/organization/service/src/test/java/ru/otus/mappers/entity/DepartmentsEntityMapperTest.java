package ru.otus.mappers.entity;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.jpa.entities.Departments;
import ru.otus.jpa.entities.Placements;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.models.organization.PlacementsModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.otus.mappers.entity.PlacementsEntityMapperTest.assertEqualsPlacementsModelAndEntity;

@SpringBootTest
@ActiveProfiles("test")
class DepartmentsEntityMapperTest {
    private static final Logger log = LoggerFactory.getLogger(DepartmentsEntityMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";

    @Autowired
    private DepartmentsEntityMapper departmentsEntityMapper;

    @Test
    void checkToEntityMapping() {
        var model = DepartmentsModel.builder().build();
        var entity = departmentsEntityMapper.toEntity(model);
        assertEqualsDepartmentsModelAndEntity(model, entity);

        model = DepartmentsModel.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        entity = departmentsEntityMapper.toEntity(model);
        assertEqualsDepartmentsModelAndEntity(model, entity);
    }

    @Test
    void checkToModelMapping() {
        var entity = Departments.builder().build();
        var model = departmentsEntityMapper.toModel(entity);
        assertEqualsDepartmentsModelAndEntity(model, entity);

        entity = Departments.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        model = departmentsEntityMapper.toModel(entity);
        assertEqualsDepartmentsModelAndEntity(model, entity);
    }

    @Test
    void checkPlacementsMappingToModel() {
        var department = Departments.builder()
                .id(ID).name(NAME).description(DESCRIPTION).build();
        List<Placements> placements = List.of(Placements.builder()
                .id(ID).department(department).build());
        department.setPlacements(placements);

        var model = departmentsEntityMapper.toModel(department);

        assertNotNull(model.getPlacements());
        assertEquals(placements.size(), model.getPlacements().size());

        var placementsModels = new ArrayList<>(model.getPlacements());
        for (var i = 0; i < department.getPlacements().size(); i++) {
            assertEqualsPlacementsModelAndEntity(placementsModels.get(i), placements.get(i));
        }
    }

    @Test
    void checkPlacementsMappingToEntity() {
        var departmentModel = DepartmentsModel.builder()
                .id(ID).name(NAME).description(DESCRIPTION).build();
        List<PlacementsModel> placementsModels = List.of(PlacementsModel.builder()
                .id(ID).department(departmentModel).build());
        departmentModel.setPlacements(placementsModels);

        var entity = departmentsEntityMapper.toEntity(departmentModel);

        assertNotNull(entity.getPlacements());
        assertEquals(placementsModels.size(), entity.getPlacements().size());

        var placements = new ArrayList<>(entity.getPlacements());
        for (var i = 0; i < departmentModel.getPlacements().size(); i++) {
            assertEqualsPlacementsModelAndEntity(placementsModels.get(i), placements.get(i));
        }
    }


    private void assertEqualsDepartmentsModelAndEntity(DepartmentsModel model, Departments entity) {
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getDescription(), entity.getDescription());
        assertEquals(model.isActive(), entity.isActive());
    }

}