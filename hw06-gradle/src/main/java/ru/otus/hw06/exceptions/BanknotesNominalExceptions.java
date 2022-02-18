package ru.otus.hw06.exceptions;

public class BanknotesNominalExceptions extends Exception{
    private final String message;

    public BanknotesNominalExceptions(String msg) {
        this.message = msg;
    }
}
