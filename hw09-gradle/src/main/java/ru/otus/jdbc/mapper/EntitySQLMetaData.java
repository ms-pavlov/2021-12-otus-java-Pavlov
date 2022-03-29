package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.sql.SQLParameter;

/**
 * Создает SQL - запросы
 */
public interface EntitySQLMetaData {
    String getSelectAllSql();

    String getSelectByIdSql(SQLParameter id);

    String getInsertSql();

    String getUpdateSql(SQLParameter id);
}
