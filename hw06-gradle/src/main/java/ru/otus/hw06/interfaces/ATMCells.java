package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMCellsExceptions;

import java.util.List;
import java.util.Map;

public interface ATMCells {

    Banknotes getBanknotesInfo();

    int getBanknotesCount();

    double getMoneyInfo();

    int giveBanknotes(int count) throws ATMCellsExceptions;

    void takeBanknotes(int count) throws ATMCellsExceptions;

}
