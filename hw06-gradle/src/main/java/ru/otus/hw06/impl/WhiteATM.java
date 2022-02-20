package ru.otus.hw06.impl;

import ru.otus.hw06.builders.WhiteIssuingBuilder;
import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.interfaces.ATM;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.ATMCellsInfo;
import ru.otus.hw06.interfaces.Issuing;

import java.util.*;
import java.util.stream.Collectors;

import static ru.otus.helpers.PropertiesHelper.errorMessage;

public class WhiteATM implements ATM {

    public final static int CELLS_COUNT = 10;
    private final List<ATMCells> atmCells;

    public WhiteATM() {
        this.atmCells = new ArrayList<>();
    }

    @Override
    public Issuing giveMoney(double sum) throws ATMExceptions {
        return giveIssuing(findIssuing(sum));

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
                throw new ATMExceptions(errorMessage("atmCellIndex", String.valueOf(atmCells.indexOf(cell)), e.getMessage()));
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
        atmCells.addAll(cellsList);
    }

    @Override
    public void removeCell(int cellIndex) throws ATMExceptions {
        try {
            atmCells.remove(cellIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new ATMExceptions(errorMessage("atmNoSuchCell"));
        }
    }

    @Override
    public void removeAll() {
        atmCells.clear();
    }

    @Override
    public List<ATMCellsInfo> getCellsInfo() {
        return atmCells.stream().map(WhiteCellsInfo::new).collect(Collectors.toList());
    }

    private void checkCapacity(double nominal, int count) throws ATMExceptions {
        if (count <= 0) {
            throw new ATMExceptions(errorMessage("atmCellWrongCount"));
        }
        var capacity = atmCells.stream().filter(cell -> cell.getNominal() == nominal).mapToInt(ATMCells::getBanknotesFreeCount).sum();
        if (capacity < count) {
            throw new ATMExceptions(errorMessage("atmToMany"));
        }
    }

    private void checkCellsPlaces(List<ATMCells> cellsList) throws ATMExceptions {
        if (cellsList.size() + atmCells.size() > CELLS_COUNT) {
            throw new ATMExceptions(errorMessage("atmFull"));
        }
    }

    private Map<Double, Integer> getNominalMap(double sum) {
        Map<Double, Integer> nominalMap = new TreeMap<>();
        for (var cell : atmCells) {
            double nominal = cell.getNominal();
            if (cell.getBanknotesCount() > 0) {
                if (nominal <= sum) {
                    nominalMap.put(cell.getNominal(), cell.getBanknotesCount());
                }
            }
        }
        return nominalMap;
    }

    private Issuing findIssuing(double sum) throws ATMExceptions {
        if (getMoneyInfo() < sum) {
            throw new ATMExceptions(errorMessage("atmLowCount"));
        }
        return findIssuing(getNominalMap(sum), sum);
    }

    private Issuing findIssuing(Map<Double, Integer> nominalMap, double sum) {
        int min = nominalMap.values().stream().mapToInt(value -> value).sum();
        Issuing result = null;
        for (Map.Entry<Double, Integer> entry : nominalMap.entrySet()) {
            Double nominal = entry.getKey();
            Integer banknoteCount = entry.getValue();
            if (banknoteCount > 0) {
                if (nominal == sum) {
                    return (new WhiteIssuingBuilder()).addBanknotes(nominal, 1).build();
                }
                if (nominal < sum) {
                    nominalMap.put(nominal, banknoteCount - 1);
                    Issuing issuing = findIssuing(nominalMap, sum - nominal);
                    nominalMap.put(nominal, banknoteCount);
                    if (null != issuing) {
                        int count = issuing.getBanknotesCount();
                        if (count < min) {
                            issuing.addCash(nominal, 1);
                            min = count;
                            result = issuing;
                        }
                    }
                }
            }
        }

        return result;
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
                throw new ATMExceptions(errorMessage("atmCellIndex", String.valueOf(atmCells.indexOf(cell)), e.getMessage()));
            }
        }

    }
}
