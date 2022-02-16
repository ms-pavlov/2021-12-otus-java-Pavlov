package ru.otus.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.Banknotes;

import static org.junit.jupiter.api.Assertions.*;

class WhiteCellsTest {

    @Test
    void getBanknotesInfo() {
        Banknotes banknotes = new SameBanknotes(100);
        ATMCells cell = new WhiteCells(banknotes);
        Assertions.assertEquals(banknotes, cell.getBanknotesInfo(), "Возвращает не копию Banknotes");

    }

    @Test
    void getBanknotesCount() {
    }

    @Test
    void getMoneyInfo() {
    }

    @Test
    void giveBanknotes() {
    }

    @Test
    void takeBanknotes() {
        ATMCells cell = new WhiteCells(new SameBanknotes(100));

    }
}