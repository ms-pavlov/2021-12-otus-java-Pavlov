package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMFactoryExceptions;

import java.util.List;
import java.util.Map;

public interface ATMFactory {

    ATM createATM();

    ATM createATM(List<ATMCells> cellsList) throws ATMFactoryExceptions;

    ATMCells createATMCell(Double nominal, int count) throws ATMFactoryExceptions;

}
