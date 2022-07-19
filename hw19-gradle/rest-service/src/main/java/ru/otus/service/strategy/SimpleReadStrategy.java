package ru.otus.service.strategy;

import org.springframework.validation.MapBindingResult;
import ru.otus.ModelEnvironment;
import ru.otus.service.utils.CRUDResponse;
import ru.otus.utils.PropertiesHelper;

import java.util.HashMap;
import java.util.Optional;

public class SimpleReadStrategy<E, M, R, Q> implements ReadStrategy<E, M, R, Q> {
    @Override
    public Optional<M> execute(Long id, ModelEnvironment<E, M, R, Q> modelEnvironment) {
        var bindingResult = new MapBindingResult(new HashMap<>(), this.toString());
        return modelEnvironment.getRepository()
                .findById(id)
                .map(entity -> modelEnvironment.getEntityMapper().toModel(entity))
                .or(() -> {
                    CRUDResponse.addBindingResult("",
                            PropertiesHelper.getFormatMessages("entityNotFound", id),
                            bindingResult);
                    return Optional.empty();
                });
    }
}
