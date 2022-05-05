package ru.otus.jdbc.mapper.exception;

public class BadEntityException extends RuntimeException {
    public BadEntityException(Exception ex) {
        super(ex);
    }

    public BadEntityException(String msg) {
        super(msg);
    }
}