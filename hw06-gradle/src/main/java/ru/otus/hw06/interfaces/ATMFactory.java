package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMFactoryExceptions;

import java.util.List;

public interface ATMFactory {

    ATM createATM();

    ATM createATM(List<ATMCells> cellsList) throws ATMFactoryExceptions;

    ATMCells createATMCell(Double nominal, int count) throws ATMFactoryExceptions;

}
