package ru.otus.hw06.impl;

import ru.otus.hw06.interfaces.Banknotes;
import ru.otus.hw06.interfaces.Issuing;

import java.util.HashMap;
import java.util.Map;

public class WhiteIssuing implements Issuing {

    private final Map<Banknotes, Integer> cash;

    public WhiteIssuing() {
        cash = new HashMap<>();
    }

    @Override
    public void addCash(Map<Banknotes, Integer> cash) {
        this.cash.putAll(cash);

    }

    @Override
    public Map<Banknotes, Integer> getCash() {
        return cash;
    }
}
