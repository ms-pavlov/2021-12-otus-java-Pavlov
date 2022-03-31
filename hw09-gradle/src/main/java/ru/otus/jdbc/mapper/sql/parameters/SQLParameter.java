package ru.otus.jdbc.mapper.sql.parameters;

public interface SQLParameter {
    String makeWhere();

    String makeWithBracket();
}
