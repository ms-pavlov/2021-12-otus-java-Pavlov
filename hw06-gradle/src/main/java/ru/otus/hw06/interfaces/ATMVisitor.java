package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMExceptions;

public interface ATMVisitor {

    Issuing findIssuing(ATM atm, double sum) throws ATMExceptions;
}
