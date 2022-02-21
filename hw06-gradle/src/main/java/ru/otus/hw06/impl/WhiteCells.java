package ru.otus.hw06.impl;

import ru.otus.hw06.assertions.ATMAssertions;
import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.interfaces.ATMCells;

import java.util.Objects;

import static ru.otus.helpers.PropertiesHelper.errorMessage;

public class WhiteCells implements ATMCells {
    public final static int MAX_COUNT = 200;
    private final double nominal;
    private int count;

    public WhiteCells(double nominal) throws ATMCellsExceptions {
        ATMAssertions.assertFalseATMCells(nominal <= 0, errorMessage("atmCellBanknotesError"));
        this.nominal = nominal;
        this.count = 0;
    }

    @Override
    public double getNominal() {
        return nominal;
    }

    @Override
    public int getBanknotesCount() {
        return this.count;
    }

    @Override
    public int getBanknotesFreeCount() {
        return MAX_COUNT - this.count;
    }

    @Override
    public double getMoneyInfo() {
        return this.count * this.nominal;
    }

    @Override
    public void giveBanknotes(int count) throws ATMCellsExceptions {
        ATMAssertions.assertFalseATMCells(count > this.count, errorMessage("atmCellsToLowCount"));
        this.count -= count;
    }

    @Override
    public void takeBanknotes(int count) throws ATMCellsExceptions {
        ATMAssertions.assertFalseATMCells(MAX_COUNT < this.count + count, errorMessage("atmCellFull"));
        ATMAssertions.assertFalseATMCells(0 >= count, errorMessage("atmCellWrongCount"));
        this.count += count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhiteCells that = (WhiteCells) o;
        return Objects.equals(nominal, that.getNominal());
    }

}
