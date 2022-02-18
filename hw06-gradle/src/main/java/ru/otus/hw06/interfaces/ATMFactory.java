package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;

import java.util.List;
import java.util.Map;

public interface ATMFactory {

    ATM createATM();

    ATM createATM(List<ATMCells> cellsList) throws ATMExceptions;

    ATMCells createATMCell(Banknotes banknotes, int count) throws ATMCellsExceptions;

    List<ATMCells> createATMCells(Map<Banknotes, Integer> banknotesMap);

}
