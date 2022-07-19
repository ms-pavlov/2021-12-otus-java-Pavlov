package ru.otus.mappers.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.dto.requests.PlacementsRequest;
import ru.otus.jpa.repositories.BuildingsRepository;
import ru.otus.jpa.repositories.DepartmentsRepository;
import ru.otus.mappers.entity.BuildingsEntityMapper;
import ru.otus.mappers.entity.DepartmentsEntityMapper;
import ru.otus.models.organization.BuildingsModel;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.models.organization.PlacementsModel;
import ru.otus.mappers.RequestMapper;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class PlacementsRequestMapper implements RequestMapper<PlacementsModel, PlacementsRequest> {
    private final BuildingsRepository buildingsRepository;
    private final DepartmentsRepository departmentsRepository;
    private final BuildingsEntityMapper buildingsEntityMapper;
    private final DepartmentsEntityMapper departmentsEntityMapper;

    @Autowired
    public PlacementsRequestMapper(BuildingsRepository buildingsRepository,
                                      DepartmentsRepository departmentsRepository,
                                      BuildingsEntityMapper buildingsEntityMapper,
                                      DepartmentsEntityMapper departmentsEntityMapper) {
        this.buildingsRepository = buildingsRepository;
        this.departmentsRepository = departmentsRepository;
        this.buildingsEntityMapper = buildingsEntityMapper;
        this.departmentsEntityMapper = departmentsEntityMapper;
    }

    private Optional<BuildingsModel> findBuildings(Long id) {
        return buildingsRepository.findById(id)
                .map(buildingsEntityMapper::toModel);
    }

    private Optional<DepartmentsModel> findDepartments(Long id) {
        return departmentsRepository.findById(id)
                .map(departmentsEntityMapper::toModel);
    }

    @Override
    public void updateModel(PlacementsModel model, PlacementsRequest request) {
        if (request == null) {
            return;
        }

        model.setActive(request.isActive());

        findDepartments(request.getDepartmentId())
                .ifPresent(model::setDepartment);
        findBuildings(request.getBuildingId())
                .ifPresent(model::setBuilding);
    }

    @Override
    public PlacementsModel createModel(PlacementsRequest request) {
        if (request == null) {
            return null;
        }

        PlacementsModel.PlacementsModelBuilder placementsModel = PlacementsModel.builder();

        placementsModel.active(request.isActive());

        findDepartments(request.getDepartmentId())
                .ifPresent(placementsModel::department);
        findBuildings(request.getBuildingId())
                .ifPresent(placementsModel::building);
        placementsModel.rooms(new ArrayList<>());
        placementsModel.contacts(new ArrayList<>());

        return placementsModel.build();
    }
}
