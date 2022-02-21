package ru.otus.hw06.impl;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.interfaces.ATM;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.ATMFactory;

import java.util.List;

public class WhiteATMFactory implements ATMFactory {
    @Override
    public ATM createATM() {
        return new WhiteATM();
    }

    @Override
    public ATM createATM(List<ATMCells> cellsList) throws ATMExceptions {
        ATM atm = new WhiteATM();
        atm.addCells(cellsList);
        return atm;
    }

    @Override
    public ATMCells createATMCell(Double nominal, int count) throws ATMCellsExceptions {
        ATMCells cell = new WhiteCells(nominal);
        cell.takeBanknotes(count);
        return cell;
    }
}
