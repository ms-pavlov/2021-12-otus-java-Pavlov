package ru.otus.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMFactoryExceptions;
import ru.otus.hw06.exceptions.BanknotesNominalExceptions;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.ATMFactory;
import ru.otus.hw06.interfaces.Banknotes;

import static ru.otus.helpers.PropertiesHelper.*;
import static ru.otus.helpers.ReflectionHelper.setFieldValue;

class WhiteCellsTest {
    ATMFactory atmFactory;

    @BeforeEach
    void before() {
        atmFactory = new WhiteATMFactory();
    }

    @Test
    void getBanknotesInfo() throws ATMCellsExceptions, ATMFactoryExceptions {

        ATMCells cell = atmFactory.createATMCell(new SameBanknotes(100), 0);
        Banknotes banknotes = cell.getBanknotesInfo();

        setFieldValue(banknotes, "nominal", 1000);

        Assertions.assertNotEquals(banknotes, cell.getBanknotesInfo(),
                testMessage("atmCellsNotImmutableBanknotes"));

    }

    @Test
    void getBanknotesCount() throws ATMCellsExceptions, ATMFactoryExceptions {
        double nominal = 100;
        int count = 10;
        ATMCells cell = atmFactory.createATMCell(new SameBanknotes(nominal), count);
        Assertions.assertEquals(count, cell.getBanknotesCount(), testMessage("atmCellsBanknotesCount"));

    }

    @Test
    void getMoneyInfo() throws ATMCellsExceptions, ATMFactoryExceptions {
        double nominal = 100;
        int count = 10;
        ATMCells cell = atmFactory.createATMCell(new SameBanknotes(nominal), count);

        Assertions.assertEquals(nominal*count, cell.getMoneyInfo(), testMessage("atmCellsMoneyInfoError"));
    }

    @Test
    void giveBanknotes() throws ATMCellsExceptions, ATMFactoryExceptions {
        double nominal = 100;
        int count = 10;

        ATMCells cell = atmFactory.createATMCell(new SameBanknotes(nominal), count);

        Assertions.assertThrows(ATMCellsExceptions.class,
                () -> cell.giveBanknotes(count+1),
                testMessage( "atmCellsToLowCount"));

        cell.giveBanknotes(count);
        Assertions.assertEquals(0,
                cell.getBanknotesCount(),
                testMessage( "atmCellsGiveBanknotes"));
    }

    @Test
    void takeBanknotes() throws ATMFactoryExceptions {
        ATMCells cell = atmFactory.createATMCell(new SameBanknotes(100), 0);

        ATMCellsExceptions atmCellsExceptions = Assertions.assertThrows(ATMCellsExceptions.class,
                () -> cell.takeBanknotes(WhiteCells.MAX_COUNT+1), testMessage("atmCellsToLowCell"));

        Assertions.assertEquals(errorMessage( "atmCellFull"), atmCellsExceptions.getMessage());
    }
}