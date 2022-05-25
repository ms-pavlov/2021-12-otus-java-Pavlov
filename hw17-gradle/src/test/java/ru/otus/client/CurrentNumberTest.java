package ru.otus.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrentNumberTest {

    @Test
    void setAndGetNumber() {
        var number = new CurrentNumber();
        number.setNumber(100L);
        assertEquals(100L, number.getNumber());
    }

}