package ru.otus.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.exceptions.ATMFactoryExceptions;
import ru.otus.hw06.interfaces.ATM;
import ru.otus.hw06.interfaces.ATMCells;
import ru.otus.hw06.interfaces.ATMFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.helpers.PropertiesHelper.testMessage;
import static ru.otus.helpers.ReflectionHelper.setFieldValue;

class WhiteATMTest {

    ATMFactory atmFactory;

    @BeforeEach
    void before() {
        atmFactory = new WhiteATMFactory();
    }


    @Test
    void giveMoney() throws ATMFactoryExceptions, ATMExceptions {

        List<ATMCells> cellsList = List.of(atmFactory.createATMCell(100.0, 10),
                atmFactory.createATMCell(60.0, 10),
                atmFactory.createATMCell(10.0, 10));

        ATM atm = atmFactory.createATM(cellsList);

        Assertions.assertThrows(ATMExceptions.class,
                () -> atm.giveMoney(10000),
                testMessage("atmLowCount"));

//        Assertions.assertEquals(1700,  atm.giveMoney(1700).getSum());

        atm.removeCell(0);
        atm.removeCell(0);
        atm.removeCell(0);

        atm.addCells(cellsList);

        Assertions.assertEquals(100,  atm.giveMoney(100).getSum());
        Assertions.assertEquals(60,  atm.giveMoney(60).getSum());
        Assertions.assertEquals(10,  atm.giveMoney(10).getSum());

        Assertions.assertEquals(9, atm.getCellsInfo().get(1).getBanknotesCount());
        Assertions.assertEquals(9, atm.getCellsInfo().get(0).getBanknotesCount());
        Assertions.assertEquals(9, atm.getCellsInfo().get(2).getBanknotesCount());

        System.out.println(atm.giveMoney(120).getCash());

        Assertions.assertEquals(120,  atm.giveMoney(120).getSum());
        Assertions.assertEquals(2,  atm.giveMoney(120).getBanknotesCount());

        Assertions.assertThrows(ATMExceptions.class, ()->atm.giveMoney(5));
    }

    @Test
    void takeMoney() throws ATMFactoryExceptions, ATMExceptions {
        List<ATMCells> atmCells = List.of(atmFactory.createATMCell(100.0, 1),
                atmFactory.createATMCell(100.0, 1));
        ATM atm = atmFactory.createATM(atmCells);

        atm.takeMoney(100.0, 1);
        Assertions.assertEquals(300, atm.getMoneyInfo());

        atm.takeMoney(100.0, 250);

        Assertions.assertEquals(25300, atm.getMoneyInfo());

        Assertions.assertThrows(ATMExceptions.class, () -> atm.takeMoney(100.0, 150));
        Assertions.assertThrows(ATMExceptions.class, () -> atm.takeMoney(100.0, 0));
        Assertions.assertThrows(ATMExceptions.class, () -> atm.takeMoney(100.0, -1));
    }

    @Test
    void getMoneyInfo() throws ATMFactoryExceptions {
        List<ATMCells> cellsList = new ArrayList<>();
        double sum = 0;
        for (var i = 0; i < 5; i++) {
            cellsList.add(atmFactory.createATMCell(i + 1.0, i + 1));
            sum += (i + 1.0) * (i + 1);
        }

        ATM atm = atmFactory.createATM(cellsList);

        Assertions.assertEquals(sum, atm.getMoneyInfo());

    }

    @Test
    void addCells() throws ATMFactoryExceptions {
        List<ATMCells> cellsList = new ArrayList<>();
        for (var i = 0; i < 12; i++) {
            cellsList.add(atmFactory.createATMCell(100.0, i+1));
        }
        ATM atm = atmFactory.createATM();

        System.out.println(cellsList.size());

        Assertions.assertThrows(ATMExceptions.class,
                () -> atm.addCells(cellsList),
                testMessage("atmFull"));


    }

    @Test
    void removeCell() {
        ATM atm = atmFactory.createATM();
        Assertions.assertThrows(ATMExceptions.class, () -> atm.removeCell(0));
    }

    @Test
    void removeAll() throws ATMFactoryExceptions {
        List<ATMCells> atmCells = List.of(atmFactory.createATMCell(100.0, 1));
        ATM atm = atmFactory.createATM(atmCells);
        atm.removeAll();
        Assertions.assertEquals(0, atm.getMoneyInfo());
    }

    @Test
    void getCellsInfo() throws ATMFactoryExceptions {

        List<ATMCells> atmCells = List.of(atmFactory.createATMCell(100.0, 1));
        ATM atm = atmFactory.createATM(atmCells);
        Assertions.assertEquals(100, atm.getMoneyInfo());

    }


}