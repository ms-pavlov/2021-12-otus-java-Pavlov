package ru.otus.mappers;

import org.mapstruct.MappingTarget;

public interface RequestMapper<M, Q>{

    void updateModel(@MappingTarget M model, Q request);

    M createModel(Q request);
}
