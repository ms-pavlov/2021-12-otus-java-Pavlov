package ru.otus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.Validator;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.service.ModelProcessor;
import ru.otus.service.repositories.CRUDModel;
import ru.otus.service.strategy.RequestStrategy;
import ru.otus.service.strategy.SoftDeleteMarker;

public interface ModelEnvironment<M, Q> {

    ModelProcessor<M> getAfterModifyProcessor();

    RequestStrategy<M, Q> getRequestStrategy();

    CRUDModel<M> getDataSource();

    SoftDeleteMarker<M> getDeleteMarker();

}
