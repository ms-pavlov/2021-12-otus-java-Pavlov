package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.mapper.exception.BadSQLRequestException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Mapper<T> {
    private final MappingStrategy<T> mappingStrategy;
    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

    public Mapper(MappingStrategy<T> mappingStrategy) {
        this.mappingStrategy = mappingStrategy;
    }

    public T makeEntity(ResultSet resultSet) {
        try {
            return resultSet.next() ? mappingStrategy.toEntityObject(resultSet) : null;
        } catch (SQLException e) {
            throw new BadSQLRequestException(e);
        }
    }

    public List<T> makeEntityList(ResultSet resultSet) {
        try {
            List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(mappingStrategy.toEntityObject(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new BadSQLRequestException(e);
        }
    }
}
