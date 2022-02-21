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
        if (atm.getMoneyInfo() < sum) {
            throw new ATMExceptions(errorMessage("atmLowCount"));
        }
        return findIssuing(getNominalMap(sum, atm.getCellsInfo()), sum);
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


    private Map<Double, Integer> getNominalMap(double sum, List<ATMCellsInfo> atmCells) {
        Map<Double, Integer> nominalMap = new TreeMap<>();
        for (var cell : atmCells) {
            double nominal = cell.getNominalInfo();
            if (cell.getBanknotesCount() > 0) {
                if (nominal <= sum) {
                    nominalMap.put(cell.getNominalInfo(), cell.getBanknotesCount());
                }
            }
        }
        return nominalMap;
    }
}
