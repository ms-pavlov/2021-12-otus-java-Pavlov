package ru.otus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.Validator;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.RequestMapper;
import ru.otus.mappers.ResponseMapper;
import ru.otus.service.ModelProcessor;
import ru.otus.service.repositories.CRUDModel;
import ru.otus.service.strategy.RequestStrategy;
import ru.otus.service.strategy.SoftDeleteMarker;

@Getter
@Builder
@AllArgsConstructor
public class ModelEnvironmentImpl<M, Q> implements ModelEnvironment<M, Q> {
    ModelProcessor<M> afterModifyProcessor;
    RequestStrategy<M, Q> requestStrategy;
    CRUDModel<M> dataSource;
    SoftDeleteMarker<M> deleteMarker;
}
