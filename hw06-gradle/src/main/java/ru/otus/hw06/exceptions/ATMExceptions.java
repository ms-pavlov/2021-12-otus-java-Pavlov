package ru.otus.hw06.exceptions;

public class ATMExceptions extends Exception {

    String message;

    public ATMExceptions(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
