package ru.otus.hw06.impl;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.BanknotesNominalExceptions;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.Banknotes;

import static ru.otus.helpers.PropertiesHelper.errorMessage;

public class WhiteCells implements ATMCells {
    public final static int MAX_COUNT = 200;
    private final Banknotes banknote;
    private int count;


    public WhiteCells(Banknotes banknote) {
        this.banknote = banknote;
        this.count = 0;
    }

    @Override
    public Banknotes getBanknotesInfo() throws ATMCellsExceptions {
        try {
            return new SameBanknotes(banknote);
        } catch (BanknotesNominalExceptions e) {
            e.printStackTrace();
            throw new ATMCellsExceptions(errorMessage("atmCellBanknotesError"));
        }
    }

    @Override
    public double getNominal() throws ATMCellsExceptions {
        try {
            return banknote.getNominal();
        } catch (BanknotesNominalExceptions e) {
            e.printStackTrace();
            throw new ATMCellsExceptions(errorMessage("atmCellBanknotesError"));
        }
    }

    @Override
    public int getBanknotesCount() {
        return this.count;
    }

    @Override
    public double getMoneyInfo() throws ATMCellsExceptions {
        try {
            return this.count * this.banknote.getNominal();
        } catch (BanknotesNominalExceptions e) {
            e.printStackTrace();
            throw new ATMCellsExceptions(errorMessage("atmCellBanknotesError"));
        }
    }

    @Override
    public int giveBanknotes(int count) throws ATMCellsExceptions {
        if (count > this.count) {
            throw new ATMCellsExceptions(errorMessage("atmCellsToLowCount"));
        }
        this.count -= count;
        return count;
    }

    @Override
    public void takeBanknotes(int count) throws ATMCellsExceptions {
        if (MAX_COUNT < this.count + count) {
            throw new ATMCellsExceptions(errorMessage("atmCellFull"));
        }
        this.count += count;
    }
}
