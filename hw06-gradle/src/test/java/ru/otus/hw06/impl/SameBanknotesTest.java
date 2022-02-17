package ru.otus.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.hw06.exceptions.BanknotesNominalExceptions;
import ru.otus.hw06.interfaces.Banknotes;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.helpers.PropertiesHelper.getProperty;
import static ru.otus.helpers.PropertiesHelper.testMessage;
import static ru.otus.helpers.ReflectionHelper.setFieldValue;

class SameBanknotesTest {

    @Test
    void copy() {
        Banknotes banknotes = new SameBanknotes(10);
        setFieldValue(banknotes, "nominal", -10);

        Assertions.assertThrows(BanknotesNominalExceptions.class,
                () -> new SameBanknotes(banknotes),
                testMessage("BanknotesNominalWrong"));
    }

    @Test
    void getNominal() throws BanknotesNominalExceptions {
        double nominal = 100;
        Banknotes banknotes = new SameBanknotes(nominal);

        Assertions.assertEquals(nominal, banknotes.getNominal(), testMessage("nominalError"));


        Assertions.assertThrows(BanknotesNominalExceptions.class,
                () -> (new SameBanknotes(-nominal)).getNominal(),
                testMessage("BanknotesNominalWrong"));

        Assertions.assertThrows(BanknotesNominalExceptions.class,
                () -> (new SameBanknotes(0)).getNominal(),
                testMessage("BanknotesNominalWrong"));
    }
}