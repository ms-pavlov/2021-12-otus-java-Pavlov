package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMExceptions;

import java.util.List;

public interface ATM {
    Issuing giveMoney(float count) throws ATMExceptions;

    void takeMoney(Banknotes banknote) throws ATMExceptions;

    double getMoneyInfo();

    void addCells(List<ATMCells> cellsList) throws ATMExceptions;

    void removeCell(int cellIndex) throws ATMExceptions;

    List<ATMCells> getCellsInfo();
}
