package ru.otus.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.hw06.interfaces.Banknotes;

import static org.junit.jupiter.api.Assertions.*;

class SameBanknotesTest {

    @Test
    void getNominal() {
        double nominal = 100;
        Banknotes banknotes = new SameBanknotes(nominal);

        Assertions.assertEquals(nominal, banknotes.getNominal(), "Неверно передаётся номинал");

    }
}