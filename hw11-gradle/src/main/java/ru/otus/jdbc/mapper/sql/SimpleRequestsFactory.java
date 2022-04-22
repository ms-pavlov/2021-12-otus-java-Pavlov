package ru.otus.jdbc.mapper.sql;

import ru.otus.jdbc.mapper.sql.requests.*;

public class SimpleRequestsFactory implements SQLRequestsFactory {
    private final RequestFieldsData requestFieldsData;

    public SimpleRequestsFactory(RequestFieldsData requestFieldsData) {
        this.requestFieldsData = requestFieldsData;
    }


    @Override
    public SQLRequestBuilder select() {
        return new SelectRequestBuilder(requestFieldsData.getAllFields(), requestFieldsData.getName());
    }

    @Override
    public SQLRequestBuilder update() {
        return new UpdateRequestBuilder(requestFieldsData.getFieldsWithoutId(), requestFieldsData.getName());
    }

    @Override
    public SQLRequestBuilder insert() {
        return new InsertRequestBuilder(requestFieldsData.getFieldsWithoutId(), requestFieldsData.getName());
    }

}
