package ru.otus.hw06.impl;

import ru.otus.hw06.builders.WhiteIssuingBuilder;
import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.interfaces.ATM;
import ru.otus.hw06.interfaces.ATMCellsInfo;
import ru.otus.hw06.interfaces.ATMVisitor;
import ru.otus.hw06.interfaces.Issuing;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static ru.otus.helpers.PropertiesHelper.errorMessage;

public class SimpleATMVisitor implements ATMVisitor {
    @Override
    public Issuing findIssuing(ATM atm, double sum) throws ATMExceptions {
        if (0 >= sum) {
            throw new ATMExceptions(errorMessage("atmSumError"));
        }
        if (atm.getMoneyInfo() < sum) {
            throw new ATMExceptions(errorMessage("atmLowCount"));
        }
        if (atm.getMoneyInfo() == sum) {
            return WhiteIssuingBuilder.builder().addCash(atm.getCellsInfo()).build();
        }
        return findIssuing(getNominalMap(sum, atm.getCellsInfo()), sum, getMaxCount(atm.getCellsInfo()));
    }

    private Issuing findIssuing(Map<Double, Integer> nominalMap, double sum, int min) {
        Issuing result = null;
        for (double nominal : nominalMap.keySet()) {
            if ((nominalMap.get(nominal) > 0) && (min > 0)) {
                if (nominal == sum) {
                    return WhiteIssuingBuilder.builder().addBanknotes(nominal, 1).build();
                }
                if (nominal < sum) {
                    int banknoteCount = nominalMap.get(nominal);
                    nominalMap.put(nominal, banknoteCount - 1);
                    Issuing issuing = findIssuing(nominalMap, sum - nominal, min - 1);

                    nominalMap.put(nominal, banknoteCount);
                    if (null != issuing) {
                        issuing.addCash(nominal, 1);
                        if (issuing.getBanknotesCount() < min) {
                            min = issuing.getBanknotesCount();
                            result = issuing;
                        }
                    }
                }
            }
        }

        return result;
    }


    private Map<Double, Integer> getNominalMap(double sum, List<ATMCellsInfo> atmCells) {
        Map<Double, Integer> nominalMap = new TreeMap<>();
        for (var cell : atmCells) {
            double nominal = cell.getNominalInfo();
            if (cell.getBanknotesCount() > 0) {
                if (nominal <= sum) {
                    nominalMap.put(nominal, nominalMap.containsKey(nominal) ? cell.getBanknotesCount() + nominalMap.get(nominal) : cell.getBanknotesCount());
                }
            }
        }
        return nominalMap;
    }

    private int getMaxCount(List<ATMCellsInfo> atmCells) {
        return atmCells.stream().mapToInt(ATMCellsInfo::getBanknotesCount).sum();
    }
}
