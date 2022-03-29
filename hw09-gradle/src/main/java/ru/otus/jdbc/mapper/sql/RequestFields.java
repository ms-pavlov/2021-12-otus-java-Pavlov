package ru.otus.jdbc.mapper.sql;

import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class RequestFields implements RequestFieldsData{
    private final String name;
    private final List<String> allFields;
    private final List<String> fieldsWithoutId;

    public RequestFields(String name, List<String> allFields, List<String> fieldsWithoutId) {
        this.name = name;
        this.allFields = allFields;
        this.fieldsWithoutId = fieldsWithoutId;
    }

    public RequestFields(EntityClassMetaData<?> entityClassMetaData) {
        this(entityClassMetaData.getName().toLowerCase(Locale.ROOT),
                entityClassMetaData.getAllFields().stream().map(Field::getName).toList(),
                entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).toList());
    }

                         @Override
    public List<String> getAllFields() {
        return allFields;
    }

    @Override
    public List<String> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

    @Override
    public String getName() {
        return name;
    }
}
