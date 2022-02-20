package ru.otus.hw06.impl;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.ATMCellsInfo;

import static ru.otus.helpers.PropertiesHelper.errorMessage;

public class WhiteCellsInfo implements ATMCellsInfo {

    private final double nominal;
    private final int count;

    public WhiteCellsInfo(ATMCells atmCells) {
        this.nominal = atmCells.getNominal();
        this.count = atmCells.getBanknotesCount();
    }

    public WhiteCellsInfo(double nominal, int count) {
        this.nominal = nominal;
        this.count = count;
    }

    @Override
    public double getNominalInfo() {
        return nominal;
    }

    @Override
    public int getBanknotesCount() {
        return count;
    }

    @Override
    public double getMoneyInfo() {
        return nominal * count;
    }
}
