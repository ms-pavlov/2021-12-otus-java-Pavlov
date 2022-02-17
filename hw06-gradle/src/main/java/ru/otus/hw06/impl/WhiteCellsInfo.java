package ru.otus.hw06.impl;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.ATMCellsInfo;
import ru.otus.hw06.interfaces.Banknotes;

public class WhiteCellsInfo implements ATMCellsInfo {

    private final double nominal;
    private final int count;

    public WhiteCellsInfo(ATMCells atmCells) throws ATMCellsExceptions {
        this.nominal = atmCells.getBanknotesNaminal();
        this.count = atmCells.getBanknotesCount();
    }

    @Override
    public double getNominalInfo()  {
        return nominal;
    }

    @Override
    public int getBanknotesCount()  {
        return count;
    }

    @Override
    public double getMoneyInfo() {
        return nominal*count;
    }
}
