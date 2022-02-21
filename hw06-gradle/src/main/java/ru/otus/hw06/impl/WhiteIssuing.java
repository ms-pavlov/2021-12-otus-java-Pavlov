package ru.otus.hw06.impl;

import ru.otus.hw06.interfaces.Issuing;

import java.util.HashMap;
import java.util.Map;

public class WhiteIssuing implements Issuing {

    private final Map<Double, Integer> cash;

    public WhiteIssuing() {
        cash = new HashMap<>();
    }

    @Override
    public void addCash(Map<Double, Integer> cash) {
        this.cash.putAll(cash);
    }

    @Override
    public void addCash(double nominal, int count) {
        cash.put(nominal, cash.containsKey(nominal) ? count + cash.get(nominal) : count);
    }

    @Override
    public Map<Double, Integer> getCash() {
        return cash;
    }

    @Override
    public double getSum() {
        return cash.entrySet().stream().mapToDouble(entry -> entry.getKey() * entry.getValue()).sum();
    }

    @Override
    public int getBanknotesCount() {
        return cash.values().stream().mapToInt(value -> value).sum();
    }
}
