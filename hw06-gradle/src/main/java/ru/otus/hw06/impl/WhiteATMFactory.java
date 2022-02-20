package ru.otus.hw06.impl;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.exceptions.ATMFactoryExceptions;
import ru.otus.hw06.interfaces.ATM;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.ATMFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WhiteATMFactory implements ATMFactory {
    @Override
    public ATM createATM() {
        return new WhiteATM();
    }

    @Override
    public ATM createATM(List<ATMCells> cellsList) throws ATMFactoryExceptions {
        ATM atm = new WhiteATM();
        try {
            atm.addCells(cellsList);
        } catch (ATMExceptions e) {
            e.printStackTrace();
            throw new ATMFactoryExceptions("");
        }
        return atm;
    }


    @Override
    public ATMCells createATMCell(Double nominal, int count) throws ATMFactoryExceptions {
        ATMCells cell;
        try {
            cell = new WhiteCells(nominal);
            cell.takeBanknotes(count);
        } catch (ATMCellsExceptions e) {
            e.printStackTrace();
            throw new ATMFactoryExceptions(e.getMessage());
        }
        return cell;
    }
}
