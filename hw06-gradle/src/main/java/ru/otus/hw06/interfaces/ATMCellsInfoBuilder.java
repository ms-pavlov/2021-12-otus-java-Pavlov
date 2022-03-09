package ru.otus.hw06.interfaces;

public interface ATMCellsInfoBuilder {

    ATMCellsInfoBuilder banknotes(double nominal);

    ATMCellsInfoBuilder count(int count);

    ATMCellsInfo build();
}
