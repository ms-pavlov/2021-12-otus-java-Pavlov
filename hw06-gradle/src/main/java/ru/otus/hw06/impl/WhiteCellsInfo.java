package ru.otus.hw06.impl;

import ru.otus.hw06.interfaces.ATMCellsInfo;

public record WhiteCellsInfo(double nominal, int count) implements ATMCellsInfo {

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
