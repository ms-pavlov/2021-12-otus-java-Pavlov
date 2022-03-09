package ru.otus.hw06.assertions;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;

public class ATMAssertions {

    public static void assertFalseATM(boolean expression, String message) throws ATMExceptions {
        if (expression) {
            throw new ATMExceptions(message);
        }
    }

    public static void failATM(String message) throws ATMExceptions {
        throw new ATMExceptions(message);
    }

    public static void assertFalseATMCells(boolean expression, String message) throws ATMCellsExceptions {
        if (expression) {
            throw new ATMCellsExceptions(message);
        }
    }
}
