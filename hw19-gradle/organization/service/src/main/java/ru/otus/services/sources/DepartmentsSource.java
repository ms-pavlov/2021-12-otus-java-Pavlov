package ru.otus.services.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.jpa.entities.Departments;
import ru.otus.mappers.EntityMapper;
import ru.otus.models.organization.DepartmentsModel;
import ru.otus.service.repositories.SimpleCRUDModel;

@Component
public class DepartmentsSource extends SimpleCRUDModel<DepartmentsModel, Departments> {
    @Autowired
    public DepartmentsSource(JpaRepository<Departments, Long> repository,
                             @Qualifier("departmentsEntityMapperImpl") EntityMapper<DepartmentsModel, Departments> entityMapper) {
        super(repository, entityMapper);
    }
}
