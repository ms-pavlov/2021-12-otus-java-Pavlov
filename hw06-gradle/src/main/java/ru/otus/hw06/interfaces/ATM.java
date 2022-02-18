package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMExceptions;

import java.util.List;

public interface ATM {
    Issuing giveMoney(float count) throws ATMExceptions;

    void takeMoney(Banknotes banknote, int count) throws ATMExceptions;

    double getMoneyInfo() throws ATMExceptions;

    void addCells(List<ATMCells> cellsList) throws ATMExceptions;

    void removeCell(int cellIndex) throws ATMExceptions;

    List<ATMCellsInfo> getCellsInfo() throws ATMExceptions;
}
