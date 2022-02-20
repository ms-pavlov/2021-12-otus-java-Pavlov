package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMCellsExceptions;

public interface ATMCells {

    double getNominal();

    int getBanknotesCount();

    int getBanknotesFreeCount();

    double getMoneyInfo();

    void giveBanknotes(int count) throws ATMCellsExceptions;

    void takeBanknotes(int count) throws ATMCellsExceptions;

}
