package ru.otus.hw06.impl;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.Banknotes;

public class WhiteCells implements ATMCells {
    private final Banknotes banknote;
    private int count;


    public WhiteCells(Banknotes banknote) {
        this.banknote = banknote;
        this.count = 0;
    }


    @Override
    public Banknotes getBanknotesInfo() {
        return banknote;
    }

    @Override
    public int getBanknotesCount() {
        return 0;
    }

    @Override
    public double getMoneyInfo() {
        return 0;
    }

    @Override
    public int giveBanknotes(int count) throws ATMCellsExceptions {
        return 0;
    }

    @Override
    public void takeBanknotes(int count) throws ATMCellsExceptions {

    }
}
