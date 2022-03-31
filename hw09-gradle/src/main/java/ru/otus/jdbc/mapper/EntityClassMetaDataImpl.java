package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.annotations.Id;
import ru.otus.jdbc.mapper.exception.BadEntityException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final static Class<? extends Annotation> ID_ANNOTATION = Id.class;
    private final String name;
    private final Field IdField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;
    private final Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.name = entityClass.getSimpleName();
        this.IdField = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ID_ANNOTATION))
                .findFirst()
                .orElseThrow(() -> new BadEntityException("Нет поля id"));
        this.allFields = Arrays.stream(entityClass.getDeclaredFields()).toList();
        this.fieldsWithoutId = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(ID_ANNOTATION))
                .toList();
        try {
            this.constructor = entityClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new BadEntityException(e);
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return IdField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
