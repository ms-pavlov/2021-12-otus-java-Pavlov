package ru.otus.hw06.impl;

import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.interfaces.ATM;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.Banknotes;
import ru.otus.hw06.interfaces.Issuing;

import java.util.List;

public class WhiteATM implements ATM {
    public static int CELLS_COUNT = 10;
    private List<ATMCells> atmCells;

    @Override
    public Issuing giveMoney(float count) throws ATMExceptions {
        return null;
    }

    @Override
    public void takeMoney(Banknotes banknote) throws ATMExceptions {

    }

    @Override
    public double getMoneyInfo() {
        return atmCells.stream().mapToDouble(ATMCells::getMoneyInfo).sum();
    }

    @Override
    public void addCells(List<ATMCells> cellsList) throws ATMExceptions {
        if (cellsList.size()+atmCells.size() > CELLS_COUNT) {
            throw new ATMExceptions("Недостаточно места для новых ячеек");
        }
        atmCells.addAll(cellsList);
    }

    @Override
    public void removeCell(int cellIndex) throws ATMExceptions {
        if (null == atmCells.get(cellIndex)) {
            throw new ATMExceptions("Нет такой ячейки");
        }

    }

    @Override
    public List<ATMCells> getCellsInfo() {

        return null;
    }
}
