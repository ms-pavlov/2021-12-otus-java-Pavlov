package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.exception.BadEntityException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public abstract class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final static Class<? extends Annotation> ID_ANNOTATION = Id.class;
    private final String name;
    private final Field IdField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;
    private final Constructor<T> constructor;

    protected EntityClassMetaDataImpl() {
        this.name = getEntityClass().getSimpleName();
        this.IdField = Arrays.stream(getEntityClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ID_ANNOTATION))
                .findFirst()
                .orElseThrow(() -> new BadEntityException("Нет поля id"));
        this.allFields = Arrays.stream(getEntityClass().getDeclaredFields()).toList();
        this.fieldsWithoutId = Arrays.stream(getEntityClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(ID_ANNOTATION))
                .toList();
        try {
            this.constructor = getEntityClass().getConstructor();
        } catch (NoSuchMethodException e) {
            throw new BadEntityException(e);
        }

    }

    public abstract Class<T> getEntityClass();

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
