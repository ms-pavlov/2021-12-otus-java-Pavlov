package ru.otus.jdbc.mapper.exception;

public class BadSQLRequestException extends RuntimeException {
    public BadSQLRequestException(Exception ex) {
        super(ex);
    }

    public BadSQLRequestException(String msg) {
        super(msg);
    }
}
