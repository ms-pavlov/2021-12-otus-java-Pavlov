package ru.otus.jdbc.mapper;

import ru.otus.crm.model.Client;
import ru.otus.jdbc.mapper.exception.BadEntityException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ClientMetaData implements EntityClassMetaData<Client> {
    @Override
    public String getName() {
        return Client.class.getSimpleName();
    }

    @Override
    public Constructor<Client> getConstructor() {
        try {
            return Client.class.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new BadEntityException(e);
        }
    }

    @Override
    public Field getIdField() {
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(Client.class.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return null;
    }
}
