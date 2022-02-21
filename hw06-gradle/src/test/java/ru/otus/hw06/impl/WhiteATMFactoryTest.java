package ru.otus.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw06.exceptions.ATMFactoryExceptions;
import ru.otus.hw06.interfaces.ATM;
import ru.otus.hw06.interfaces.ATMFactory;

class WhiteATMFactoryTest {

    ATMFactory atmFactory;

    @BeforeEach
    void before() {
        atmFactory = new WhiteATMFactory();
    }

    @Test
    void createATM() {
        Assertions.assertNotNull(atmFactory.createATM());
    }

    @Test
    void createATMCell() throws ATMFactoryExceptions {
        Assertions.assertNotNull(atmFactory.createATMCell(100.0, 1));

        Assertions.assertThrows(ATMFactoryExceptions.class, () -> atmFactory.createATMCell(-100.0, 1));
        Assertions.assertThrows(ATMFactoryExceptions.class, () -> atmFactory.createATMCell(100.0, -1));
    }

    @Test
    void testCreateATM() {

        ATM atm = atmFactory.createATM();
        Assertions.assertNotNull(atm);
    }

}