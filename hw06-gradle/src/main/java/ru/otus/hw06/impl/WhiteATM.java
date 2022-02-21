package ru.otus.hw06.impl;

import ru.otus.hw06.assertions.ATMAssertions;
import ru.otus.hw06.builders.WhiteCellsInfoBuilder;
import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.helpers.PropertiesHelper.errorMessage;

public class WhiteATM implements ATM {

    public final static int CELLS_COUNT = 10;
    private final List<ATMCells> atmCells;

    public WhiteATM() {
        this.atmCells = new ArrayList<>();
    }

    @Override
    public Issuing giveMoney(ATMVisitor atmVisitor, double sum) throws ATMExceptions {
        return giveIssuing(atmVisitor.findIssuing(this, sum));
    }

    @Override
    public void takeMoney(double nominal, int count) throws ATMExceptions {
        checkCapacity(nominal, count);
        for (var cell : atmCells.stream().filter(cell -> cell.getNominal() == nominal).toList()) {
            try {
                var free = cell.getBanknotesFreeCount();
                if (free < count) {
                    cell.takeBanknotes(free);
                    count -= free;
                } else {
                    cell.takeBanknotes(count);
                    break;
                }
            } catch (ATMCellsExceptions e) {
                ATMAssertions.failATM(errorMessage("atmCellIndex", String.valueOf(atmCells.indexOf(cell)), e.getMessage()));
            }
        }
    }

    @Override
    public double getMoneyInfo() {
        return atmCells.stream().mapToDouble(ATMCells::getMoneyInfo).sum();
    }

    @Override
    public void addCells(List<ATMCells> cellsList) throws ATMExceptions {
        checkCellsPlaces(cellsList);
        ATMAssertions.assertFalseATM(
                cellsList.stream()
                        .anyMatch(cells -> (0 >= cells.getNominal()) || (0 >= cells.getBanknotesCount())),
                errorMessage("atmWrongCellsList"));
        atmCells.addAll(cellsList);
    }

    @Override
    public void removeCell(int cellIndex) throws ATMExceptions {
        try {
            atmCells.remove(cellIndex);
        } catch (IndexOutOfBoundsException e) {
            ATMAssertions.failATM( errorMessage("atmNoSuchCell"));
        }
    }

    @Override
    public void removeAll() {
        atmCells.clear();
    }

    @Override
    public List<ATMCellsInfo> getCellsInfo() {
        return atmCells.stream()
                .map(cell -> WhiteCellsInfoBuilder.builder()
                        .banknotes(cell.getNominal())
                        .count(cell.getBanknotesCount())
                        .build())
                .collect(Collectors.toList());
    }

    private void checkCapacity(double nominal, int count) throws ATMExceptions {
        ATMAssertions.assertFalseATM(count <= 0, errorMessage("atmCellWrongCount"));
        ATMAssertions.assertFalseATM(
                count > atmCells.stream().filter(cell -> cell.getNominal() == nominal).mapToInt(ATMCells::getBanknotesFreeCount).sum(),
                errorMessage("atmToMany"));
    }

    private void checkCellsPlaces(List<ATMCells> cellsList) throws ATMExceptions {
        ATMAssertions.assertFalseATM(
                cellsList.size() + atmCells.size() > CELLS_COUNT,
                errorMessage("atmFull"));
    }

    private Issuing giveIssuing(Issuing issuing) throws ATMExceptions {
        Map<Double, Integer> cash = Optional.ofNullable(issuing)
                .orElseThrow(() -> new ATMExceptions(errorMessage("atmWrongSum")))
                .getCash();
        for (Map.Entry<Double, Integer> e : cash.entrySet()) {
            giveFromCells(e.getKey(), e.getValue());
        }
        return issuing;
    }

    private void giveFromCells(double nominal, int count) throws ATMExceptions {
        ATMAssertions.assertFalseATM( 0 >= count, errorMessage("atmCellWrongCount"));
        for (ATMCells cell : atmCells.stream().filter(cell -> (cell.getNominal() == nominal) && (cell.getBanknotesCount() > 0)).toList()) {
            try {
                if (cell.getBanknotesCount() < count) {
                    count -= cell.getBanknotesCount();
                    cell.giveBanknotes(cell.getBanknotesCount());
                } else {
                    cell.giveBanknotes(count);
                    break;
                }
            } catch (ATMCellsExceptions e) {
                ATMAssertions.failATM(errorMessage("atmCellIndex", String.valueOf(atmCells.indexOf(cell)), e.getMessage()));
            }
        }

    }
}
