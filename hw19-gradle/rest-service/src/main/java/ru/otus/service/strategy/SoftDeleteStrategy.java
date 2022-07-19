package ru.otus.service.strategy;

import org.springframework.validation.MapBindingResult;
import ru.otus.ModelEnvironment;
import ru.otus.service.utils.CRUDResponse;
import ru.otus.utils.PropertiesHelper;

import java.util.HashMap;
import java.util.Optional;

public class SoftDeleteStrategy<E, M, R, Q> implements DeleteStrategy<E, M, R, Q> {
    private final SoftDeleteMarker<M> deleteMarker;

    public SoftDeleteStrategy(SoftDeleteMarker<M> deleteMarker) {
        this.deleteMarker = deleteMarker;
    }

    @Override
    public Optional<M> execute(Long id,
                               ModelEnvironment<E, M, R, Q> modelEnvironment) {
        var bindingResult = new MapBindingResult(new HashMap<>(), this.toString());
        return modelEnvironment.getRepository()
                .findById(id)
                .map(entity -> modelEnvironment.getEntityMapper().toModel(entity))
                .map(deleteMarker::markDeleted)
                .or(() -> {
                    CRUDResponse.addBindingResult("",
                            PropertiesHelper.getFormatMessages("entityNotFound", id),
                            bindingResult);
                    return Optional.empty();
                });
    }
}
