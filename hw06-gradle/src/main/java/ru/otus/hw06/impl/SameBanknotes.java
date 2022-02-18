package ru.otus.hw06.impl;

import ru.otus.hw06.interfaces.Banknotes;

public class SameBanknotes implements Banknotes {
    private final double nominal;

    public SameBanknotes(double nominal) {
        this.nominal = nominal;
    }

    @Override
    public double getNominal() {
        return nominal;
    }

}
