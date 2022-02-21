package ru.otus.hw06.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class WhiteIssuingBuilderTest {

    @Test
    void addBanknotes() {
        Assertions.assertEquals(100.0,
        WhiteIssuingBuilder.builder().addBanknotes(100,1).build().getSum());
    }

    @Test
    void addCash() {
        Assertions.assertEquals(100.0,
                WhiteIssuingBuilder.builder()
                        .addCash(List.of(WhiteCellsInfoBuilder.builder().banknotes(100.0).count(1).build()))
                        .build()
                        .getSum());
    }

    @Test
    void testAddCash() {
        Assertions.assertEquals(100.0,
                WhiteIssuingBuilder.builder()
                        .addCash(Map.of(100.0, 1))
                        .build()
                        .getSum());
    }
}