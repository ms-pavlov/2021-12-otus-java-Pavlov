package ru.otus.hw06.interfaces;

import ru.otus.hw06.exceptions.BanknotesNominalExceptions;

public interface Banknotes {
    double getNominal () throws BanknotesNominalExceptions;
}
