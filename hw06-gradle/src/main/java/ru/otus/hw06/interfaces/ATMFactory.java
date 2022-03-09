package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;

import java.util.List;

public interface ATMFactory {

    ATM createATM();

    ATM createATM(List<ATMCells> cellsList) throws ATMExceptions;

    ATMCells createATMCell(Double nominal, int count) throws ATMCellsExceptions;

}
