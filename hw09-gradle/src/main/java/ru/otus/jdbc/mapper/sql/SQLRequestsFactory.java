package ru.otus.jdbc.mapper.sql;

import ru.otus.jdbc.mapper.sql.requests.SQLRequestBuilder;

public interface SQLRequestsFactory {

    SQLRequestBuilder select();

    SQLRequestBuilder update();

    SQLRequestBuilder insert();

}
