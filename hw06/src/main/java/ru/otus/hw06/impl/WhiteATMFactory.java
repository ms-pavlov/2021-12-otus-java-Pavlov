package ru.otus.hw06.impl;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.exceptions.ATMFactoryExceptions;
import ru.otus.hw06.interfaces.ATM;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.ATMFactory;
import ru.otus.hw06.interfaces.Banknotes;

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
    public ATMCells createATMCell(Banknotes banknotes, int count) throws ATMFactoryExceptions {
        ATMCells cell = new WhiteCells(banknotes);
        try {
            cell.takeBanknotes(count);
        } catch (ATMCellsExceptions e) {
            e.printStackTrace();
            throw new ATMFactoryExceptions("");
        }
        return cell;
    }

    @Override
    public List<ATMCells> createATMCells(Map<Banknotes, Integer> nominalMap) throws ATMFactoryExceptions {
        List<ATMCells> atmCells = new ArrayList<>();
        for (var entry : nominalMap.entrySet()) {
            atmCells.add(createATMCell(entry.getKey(), entry.getValue()));
        }
        return atmCells;
    }
}
