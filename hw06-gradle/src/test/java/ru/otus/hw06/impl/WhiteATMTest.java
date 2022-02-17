package ru.otus.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.exceptions.ATMFactoryExceptions;
import ru.otus.hw06.interfaces.ATM;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.ATMFactory;
import ru.otus.hw06.interfaces.Banknotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static ru.otus.helpers.PropertiesHelper.testMessage;
import static ru.otus.helpers.ReflectionHelper.setFieldValue;

class WhiteATMTest {

    ATMFactory atmFactory;

    @BeforeEach
    void before() {
        atmFactory = new WhiteATMFactory();
    }


    @Test
    void giveMoney() {
    }

    @Test
    void takeMoney() {
    }

    @Test
    void getMoneyInfo() throws ATMCellsExceptions, ATMExceptions, ATMFactoryExceptions {
        Map<Banknotes, Integer> nominal = new HashMap();
        double sum = 0;
        for (var i = 0; i < 5; i++) {
            nominal.put(new SameBanknotes(i + 1.0), i + 1);
            sum += (i + 1.0) * (i + 1);
        }
        List<ATMCells> cellsList = atmFactory.createATMCells(nominal);

        ATM atm = atmFactory.createATM(cellsList);

        Assertions.assertEquals(sum, atm.getMoneyInfo());


        ATMCells atmCells = cellsList.get(0);

        Banknotes banknotes = new SameBanknotes(1);

        setFieldValue(banknotes, "nominal", -10);
        setFieldValue(atmCells, "banknote", banknotes);

        Assertions.assertThrows(ATMExceptions.class, () -> atm.getMoneyInfo());

    }

    @Test
    void addCells() throws ATMExceptions, ATMFactoryExceptions {
        Map<Banknotes, Integer> nominal = new HashMap();
        for (var i = 0; i < 12; i++) {
            nominal.put(new SameBanknotes(100), i);
        }
        ATM atm = atmFactory.createATM();

        System.out.println(atmFactory.createATMCells(nominal).size());

        Assertions.assertThrows(ATMExceptions.class,
                () -> atm.addCells(atmFactory.createATMCells(nominal)),
                testMessage("atmFull"));


    }

    @Test
    void removeCell() {
        ATM atm = atmFactory.createATM();
        Assertions.assertThrows(ATMExceptions.class, () -> atm.removeCell(0));
    }

    @Test
    void getCellsInfo() throws ATMExceptions, ATMCellsExceptions, ATMFactoryExceptions {
        Map<Banknotes, Integer> map = Map.ofEntries(entry(new SameBanknotes(100.0), 1));
        ATM atm = atmFactory.createATM(atmFactory.createATMCells(map));

        setFieldValue(atm.getCellsInfo().get(0), "banknote", new SameBanknotes(10));

        Assertions.assertEquals(100, atm.getMoneyInfo());

    }


}