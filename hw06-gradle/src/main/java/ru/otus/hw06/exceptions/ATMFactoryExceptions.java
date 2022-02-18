package ru.otus.hw06.exceptions;

public class ATMFactoryExceptions extends Throwable {
    private final String message;

    public ATMFactoryExceptions(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
