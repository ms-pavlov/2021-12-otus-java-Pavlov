package ru.otus.hw06.impl;

import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.interfaces.*;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.helpers.PropertiesHelper.errorMessage;

public class WhiteATM implements ATM {
    public final static int CELLS_COUNT = 10;
    private final List<ATMCells> atmCells;

    public WhiteATM() {
        this.atmCells = new ArrayList<>();
    }

    @Override
    public Issuing giveMoney(float count) throws ATMExceptions {
        return null;
    }

    @Override
    public void takeMoney(Banknotes banknote, int count) throws ATMExceptions {

    }

    @Override
    public double getMoneyInfo() throws ATMExceptions {
        return checkCellsAndSum(this.atmCells);
    }

    @Override
    public void addCells(List<ATMCells> cellsList) throws ATMExceptions {
        checkCellsPlaces(cellsList);
        checkCellsAndSum(cellsList);
        atmCells.addAll(cellsList);
    }

    @Override
    public void removeCell(int cellIndex) throws ATMExceptions {
        try {
            atmCells.remove(cellIndex);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new ATMExceptions(errorMessage("atmNoSuchCell"));
        }
    }

    @Override
    public List<ATMCellsInfo> getCellsInfo() {
        List<ATMCellsInfo> cellsInfos = new ArrayList<>();
        atmCells.forEach(cell -> cellsInfos.add(new WhiteCellsInfo(cell)));

        return null;
    }

    private void checkCellsPlaces(List<ATMCells> cellsList) throws ATMExceptions {
        if (cellsList.size() + atmCells.size() > CELLS_COUNT) {
            throw new ATMExceptions(errorMessage("atmFull"));
        }
    }

    private double checkCellsAndSum(List<ATMCells> cellsList) throws ATMExceptions {
        double sum = 0;
        for (ATMCells atmCell : cellsList) {
            try {
                sum += atmCell.getMoneyInfo();
            } catch (ATMCellsExceptions e) {
                e.printStackTrace();
                throw new ATMExceptions(errorMessage("atmBanknotesError").replaceAll("%name%", String.valueOf(atmCell)));
            }
        }
        return sum;
    }
}
