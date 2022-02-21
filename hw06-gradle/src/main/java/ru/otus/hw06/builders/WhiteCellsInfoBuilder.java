package ru.otus.hw06.builders;

import ru.otus.hw06.impl.WhiteCellsInfo;
import ru.otus.hw06.interfaces.ATMCellsInfo;
import ru.otus.hw06.interfaces.ATMCellsInfoBuilder;

public class WhiteCellsInfoBuilder implements ATMCellsInfoBuilder {
    private double nominal;
    private int count;

    public static ATMCellsInfoBuilder builder() {
        return new WhiteCellsInfoBuilder();
    }

    @Override
    public ATMCellsInfoBuilder banknotes(double nominal) {
        this.nominal = nominal;
        return this;
    }

    @Override
    public ATMCellsInfoBuilder count(int count) {
        this.count = count;
        return this;
    }

    @Override
    public ATMCellsInfo build() {
        return new WhiteCellsInfo(nominal, count);
    }
}
