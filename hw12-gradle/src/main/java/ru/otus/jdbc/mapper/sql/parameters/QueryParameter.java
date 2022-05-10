package ru.otus.jdbc.mapper.sql.parameters;

import java.util.Objects;

public class QueryParameter implements SQLParameter {
    private static final String QUESTION_MARK= "?";

    private final String name;
    private final String operator;

    public QueryParameter(String name, String operator) {
        this.name = name;
        this.operator = operator;
    }

    public String makeWhere() {
        return name.concat(operator).concat(QUESTION_MARK);
    }

    @Override
    public String makeWithBracket() {
        return "(".concat(makeWhere()).concat(")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryParameter that = (QueryParameter) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(operator, that.operator);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        return result;
    }
}
