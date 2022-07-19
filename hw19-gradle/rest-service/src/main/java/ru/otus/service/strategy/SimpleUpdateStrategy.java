package ru.otus.service.strategy;

import org.springframework.validation.MapBindingResult;
import ru.otus.ModelEnvironment;
import ru.otus.service.utils.CRUDResponse;
import ru.otus.utils.PropertiesHelper;

import java.util.HashMap;
import java.util.Optional;

public class SimpleUpdateStrategy<E, M, R, Q> implements UpdateStrategy<E, M, R, Q> {
    @Override
    public Optional<M> execute(Long id, Q request, ModelEnvironment<E, M, R, Q> modelEnvironment) {
        var bindingResult = new MapBindingResult(new HashMap<>(), this.toString());
        modelEnvironment.getValidator().validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            return Optional.empty();
        }
        return modelEnvironment.getRepository()
                .findById(id)
                .map(entity -> modelEnvironment.getEntityMapper().toModel(entity))
                .map(model -> {
                    modelEnvironment.getRequestMapper().updateModel(model, request);
                    return model;
                })
                .or(() -> {
                    CRUDResponse.addBindingResult("",
                            PropertiesHelper.getFormatMessages("entityNotFound", id),
                            bindingResult);
                    return Optional.empty();
                });
    }
}
