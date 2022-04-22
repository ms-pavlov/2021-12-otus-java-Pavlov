package ru.otus.jdbc.mapper.strategy;

import java.sql.ResultSet;

public interface MappingStrategy<T> {
    T toEntityObject(ResultSet resultSet);
}
