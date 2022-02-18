package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMFactoryExceptions;

import java.util.List;
import java.util.Map;

public interface ATMFactory {

    ATM createATM();

    ATM createATM(List<ATMCells> cellsList) throws ATMFactoryExceptions;

    ATMCells createATMCell(Banknotes banknotes, int count) throws ATMFactoryExceptions;

    List<ATMCells> createATMCells(Map<Banknotes, Integer> banknotesMap) throws ATMFactoryExceptions;

}
