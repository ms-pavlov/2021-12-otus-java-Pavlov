package ru.otus.hw06.exceptions;

public class ATMCellsExceptions extends Exception{
    private final String message;

    public ATMCellsExceptions(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
