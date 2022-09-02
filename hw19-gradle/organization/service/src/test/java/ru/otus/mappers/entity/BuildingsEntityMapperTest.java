package ru.otus.mappers.entity;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.jpa.entities.Buildings;
import ru.otus.jpa.entities.Placements;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.models.organization.PlacementsModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.otus.mappers.entity.PlacementsEntityMapperTest.assertEqualsPlacementsModelAndEntity;

@SpringBootTest
@ActiveProfiles("test")
class BuildingsEntityMapperTest {
    private static final Logger log = LoggerFactory.getLogger(BuildingsEntityMapperTest.class);

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "Description";


    @Autowired
    private BuildingsEntityMapper buildingsEntityMapper;

    @Test
    void checkToEntityMapping() {
        var model = BuildingsModel.builder().build();
        var entity = buildingsEntityMapper.toEntity(model);
        assertEqualsBuildingsModelAndEntity(model, entity);

        model = BuildingsModel.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        entity = buildingsEntityMapper.toEntity(model);
        assertEqualsBuildingsModelAndEntity(model, entity);
    }

    @Test
    void checkToModelMapping() {
        var entity = Buildings.builder().build();
        var model = buildingsEntityMapper.toModel(entity);
        assertEqualsBuildingsModelAndEntity(model, entity);

        entity = Buildings.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .build();
        model = buildingsEntityMapper.toModel(entity);
        assertEqualsBuildingsModelAndEntity(model, entity);
    }

    @Test
    void checkPlacementsMappingToModel() {
        var buildings = Buildings.builder()
                .id(ID).name(NAME).description(DESCRIPTION).active(true).build();
        List<Placements> placements = List.of(Placements.builder()
                .id(ID).building(buildings).build());
        buildings.setPlacements(placements);

        var model = buildingsEntityMapper.toModel(buildings);

        assertNotNull(model.getPlacements());
        assertEquals(placements.size(), model.getPlacements().size());

        var placementsModels = new ArrayList<>(model.getPlacements());
        for (var i = 0; i < buildings.getPlacements().size(); i++) {
            assertEqualsPlacementsModelAndEntity(placementsModels.get(i), placements.get(i));
        }
    }

    @Test
    void checkPlacementsMappingToEntity() {
        var buildingsModel = BuildingsModel.builder()
                .id(ID).name(NAME).description(DESCRIPTION).active(true).build();
        List<PlacementsModel> placementsModels = List.of(PlacementsModel.builder()
                .id(ID).building(buildingsModel).build());
        buildingsModel.setPlacements(placementsModels);

        var entity = buildingsEntityMapper.toEntity(buildingsModel);

        assertNotNull(entity.getPlacements());
        assertEquals(placementsModels.size(), entity.getPlacements().size());

        var placements = new ArrayList<>(entity.getPlacements());
        for (var i = 0; i < buildingsModel.getPlacements().size(); i++) {
            assertEqualsPlacementsModelAndEntity(placementsModels.get(i), placements.get(i));
        }
    }

    private void assertEqualsBuildingsModelAndEntity(BuildingsModel model, Buildings entity) {
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getDescription(), entity.getDescription());
        assertEquals(model.isActive(), entity.isActive());
    }

}