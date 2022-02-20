package ru.otus.hw06.interfaces;

import java.util.Map;

public interface Issuing {

    void addCash(Map<Double, Integer> cash);

    void addCash(double nominal, int count);

    Map<Double, Integer> getCash();

    double getSum();

    int getBanknotesCount();
}
