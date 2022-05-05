package ru.otus.jdbc.mapper;

import java.util.List;

public interface ParameterFromEntity<T> {
    List<Object> getParameterFromEntityWithId(T client);

    List<Object> getParameterFromEntityWithoutId(T client);

    List<Object> addID(List<Object> list, Object id);
}
