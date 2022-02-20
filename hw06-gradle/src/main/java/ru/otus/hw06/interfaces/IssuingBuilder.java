package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.ATMCellsExceptions;

import java.util.List;
import java.util.Map;

public interface IssuingBuilder {

    IssuingBuilder addBanknotes(double nominal, int count);

    IssuingBuilder addCash(Map<Double, Integer> cash);

    IssuingBuilder addCash(List<ATMCells> cellsList) throws ATMCellsExceptions;

    Issuing build();
}
