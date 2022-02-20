package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMExceptions;

import java.util.List;

public interface ATM {
    Issuing giveMoney(double sum) throws ATMExceptions;

    void takeMoney(double nominal, int count) throws ATMExceptions;

    double getMoneyInfo();

    List<ATMCellsInfo> getCellsInfo();

    void addCells(List<ATMCells> cellsList) throws ATMExceptions;

    void removeCell(int cellIndex) throws ATMExceptions;

    void removeAll();
}
