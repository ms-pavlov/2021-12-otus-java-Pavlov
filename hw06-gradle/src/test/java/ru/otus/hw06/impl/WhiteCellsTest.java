package ru.otus.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMFactoryExceptions;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.ATMFactory;

import static ru.otus.helpers.PropertiesHelper.errorMessage;
import static ru.otus.helpers.PropertiesHelper.testMessage;

class WhiteCellsTest {
    ATMFactory atmFactory;

    @BeforeEach
    void before() {
        atmFactory = new WhiteATMFactory();
    }


    @Test
    void getBanknotesCount() throws ATMFactoryExceptions {
        double nominal = 100;
        int count = 10;
        ATMCells cell = atmFactory.createATMCell(nominal, count);
        Assertions.assertEquals(count, cell.getBanknotesCount(), testMessage("atmCellsBanknotesCount"));

    }

    @Test
    void getMoneyInfo() throws ATMFactoryExceptions {
        double nominal = 100;
        int count = 10;
        ATMCells cell = atmFactory.createATMCell(nominal, count);

        Assertions.assertEquals(nominal * count, cell.getMoneyInfo(), testMessage("atmCellsMoneyInfoError"));
    }

    @Test
    void giveBanknotes() throws ATMCellsExceptions, ATMFactoryExceptions {
        double nominal = 100;
        int count = 10;

        ATMCells cell = atmFactory.createATMCell(nominal, count);

        Assertions.assertThrows(ATMCellsExceptions.class,
                () -> cell.giveBanknotes(count + 1),
                testMessage("atmCellsToLowCount"));

        cell.giveBanknotes(count);
        Assertions.assertEquals(0,
                cell.getBanknotesCount(),
                testMessage("atmCellsGiveBanknotes"));
    }

    @Test
    void takeBanknotes() throws ATMFactoryExceptions {
        ATMCells cell = atmFactory.createATMCell(100.0, 1);

        ATMCellsExceptions atmCellsExceptions = Assertions.assertThrows(ATMCellsExceptions.class,
                () -> cell.takeBanknotes(WhiteCells.MAX_COUNT + 1), testMessage("atmCellsToLowCell"));

        Assertions.assertEquals(errorMessage("atmCellFull"), atmCellsExceptions.getMessage());

        Assertions.assertThrows(ATMCellsExceptions.class, () -> cell.takeBanknotes(-1));
    }

    @Test
    void equalsTest() throws ATMFactoryExceptions {
        ATMCells cell1 = atmFactory.createATMCell(100.0, 1);
        ATMCells cell2 = atmFactory.createATMCell(100.0, 10);

        Assertions.assertEquals(cell1, cell2);
    }


}