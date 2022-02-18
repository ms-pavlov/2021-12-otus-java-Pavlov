package ru.otus.hw06.impl;

import ru.otus.hw06.exceptions.BanknotesNominalExceptions;
import ru.otus.hw06.interfaces.Banknotes;

import static ru.otus.helpers.PropertiesHelper.errorMessage;

public class SameBanknotes implements Banknotes {
    private final double nominal;

    public SameBanknotes(double nominal) {
        this.nominal = nominal;
    }

    public SameBanknotes(Banknotes banknotes) throws BanknotesNominalExceptions {
        this.nominal = banknotes.getNominal();
    }

    @Override
    public double getNominal() throws BanknotesNominalExceptions {
        if (0 >= nominal) {
            throw new BanknotesNominalExceptions(errorMessage("banknotesNominalError"));
        }
        return nominal;
    }

}
