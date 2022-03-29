package ru.otus.jdbc.mapper.sql;

public interface SQLRequestsFactory {

    SQLRequestBuilder select();

    SQLRequestBuilder update();

    SQLRequestBuilder insert();

    SQLRequestBuilder delete();

}
