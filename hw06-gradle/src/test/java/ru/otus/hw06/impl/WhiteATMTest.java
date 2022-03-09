package ru.otus.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06.exceptions.ATMCellsExceptions;
import ru.otus.hw06.exceptions.ATMExceptions;
import ru.otus.hw06.interfaces.*;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.helpers.PropertiesHelper.testMessage;

class WhiteATMTest {

    ATMFactory atmFactory;

    @BeforeEach
    void before() {
        atmFactory = new WhiteATMFactory();
    }


    @Test
    void giveMoney() throws ATMExceptions, ATMCellsExceptions {

        ATM atm = atmFactory.createATM(List.of(atmFactory.createATMCell(100.0, 10),
                atmFactory.createATMCell(60.0, 10),
                atmFactory.createATMCell(10.0, 10)));

        ATMVisitor atmVisitor = new SimpleATMVisitor();

        Assertions.assertThrows(ATMExceptions.class,
                () -> atm.giveMoney(atmVisitor, 10000),
                testMessage("atmLowCount"));

        Assertions.assertEquals(1700,  atm.giveMoney(atmVisitor,1700).getSum());

        atm.removeAll();

        atm.addCells(List.of(atmFactory.createATMCell(100.0, 10),
                atmFactory.createATMCell(60.0, 10),
                atmFactory.createATMCell(10.0, 10)));

        Assertions.assertEquals(1000,  atm.giveMoney(atmVisitor,1000).getSum());

        atm.removeAll();

        atm.addCells(List.of(atmFactory.createATMCell(100.0, 10),
                atmFactory.createATMCell(60.0, 10),
                atmFactory.createATMCell(10.0, 10)));

        Assertions.assertEquals(100, atm.giveMoney(atmVisitor, 100).getSum());
        Assertions.assertEquals(60, atm.giveMoney(atmVisitor, 60).getSum());
        Assertions.assertEquals(10, atm.giveMoney(atmVisitor, 10).getSum());

        Assertions.assertEquals(9, atm.getCellsInfo().get(1).getBanknotesCount());
        Assertions.assertEquals(9, atm.getCellsInfo().get(0).getBanknotesCount());
        Assertions.assertEquals(9, atm.getCellsInfo().get(2).getBanknotesCount());

        Assertions.assertEquals(120, atm.giveMoney(atmVisitor, 120).getSum());
        Assertions.assertEquals(2, atm.giveMoney(atmVisitor, 120).getBanknotesCount());

        Assertions.assertThrows(ATMExceptions.class, () -> atm.giveMoney(atmVisitor, 5));
    }

    @Test
    void takeMoney() throws ATMExceptions, ATMCellsExceptions {
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
        Assertions.assertThrows(ATMExceptions.class, () -> atm.takeMoney(-100.0, 1));
    }

    @Test
    void getMoneyInfo() throws ATMExceptions, ATMCellsExceptions {
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
    void addCells() throws ATMCellsExceptions {
        List<ATMCells> cellsList = new ArrayList<>();
        for (var i = 0; i < 12; i++) {
            cellsList.add(atmFactory.createATMCell(100.0, i + 1));
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
    void removeAll() throws ATMCellsExceptions, ATMExceptions {
        List<ATMCells> atmCells = List.of(atmFactory.createATMCell(100.0, 1));
        ATM atm = atmFactory.createATM(atmCells);
        atm.removeAll();
        Assertions.assertEquals(0, atm.getMoneyInfo());
    }

    @Test
    void getCellsInfo() throws ATMCellsExceptions, ATMExceptions {

        List<ATMCells> atmCells = List.of(atmFactory.createATMCell(100.0, 1));
        ATM atm = atmFactory.createATM(atmCells);
        Assertions.assertEquals(atm.getMoneyInfo(), atm.getCellsInfo().stream().mapToDouble(ATMCellsInfo::getMoneyInfo).sum());

    }


}