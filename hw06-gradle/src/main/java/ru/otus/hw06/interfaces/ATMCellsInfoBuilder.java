package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMCellsExceptions;

public interface ATMCellsInfoBuilder {
    ATMCellsInfoBuilder builder();

    ATMCellsInfoBuilder banknotes(double nominal);

    ATMCellsInfoBuilder count(int count);

    ATMCellsInfo build() throws ATMCellsExceptions;
}
