package ru.otus.jdbc.mapper.sql.parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryParameterGroup implements SQLParameter {

    private final List<QueryParameter> parameters;
    private final String operator;

    public QueryParameterGroup(String operator) {
        this.parameters = new ArrayList<>();
        this.operator = operator;
    }

    public void add(QueryParameter parameter) {
        if (!parameters.contains(parameter)) {
            parameters.add(parameter);
        }
    }

    @Override
    public String makeWhere() {
        return parameters.isEmpty() ? "" : parameters.stream()
                .map(SQLParameter::makeWithBracket)
                .collect(Collectors.joining(operator));
    }

    @Override
    public String makeWithBracket() {
        return "(".concat(makeWhere()).concat(")");
    }

}
