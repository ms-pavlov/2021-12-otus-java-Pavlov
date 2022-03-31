package ru.otus.jdbc.mapper;

import java.sql.ResultSet;

public interface MappingStrategy<T> {
    T toEntityObject(ResultSet resultSet);
}
