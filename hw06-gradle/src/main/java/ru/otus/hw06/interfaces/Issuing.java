package ru.otus.hw06.interfaces;

import java.util.Map;

public interface Issuing {

    void addCash (Map<Banknotes, Integer> cash);

    Map<Banknotes, Integer> getCash ();
}
