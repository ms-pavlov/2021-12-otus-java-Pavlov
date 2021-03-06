package ru.otus.jdbc.mapper.sql.requests;

import java.util.List;

public interface RequestFieldsData {
    List<String> getAllFields();

    List<String> getFieldsWithoutId();

    String getName();
}
