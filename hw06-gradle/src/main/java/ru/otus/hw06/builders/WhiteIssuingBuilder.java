package ru.otus.hw06.builders;

import ru.otus.hw06.impl.WhiteIssuing;
import ru.otus.hw06.interfaces.ATMCellsInfo;
import ru.otus.hw06.interfaces.Issuing;
import ru.otus.hw06.interfaces.IssuingBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhiteIssuingBuilder implements IssuingBuilder {
    private final Map<Double, Integer> cash;

    private WhiteIssuingBuilder() {
        this.cash = new HashMap<>();
    }

    public static  IssuingBuilder builder() {
        return new WhiteIssuingBuilder();
    }

    @Override
    public IssuingBuilder addBanknotes(double nominal, int count) {
        this.cash.put(nominal, count);
        return this;
    }

    @Override
    public IssuingBuilder addCash(Map<Double, Integer> cash) {
        this.cash.putAll(cash);
        return this;
    }

    @Override
    public IssuingBuilder addCash(List<ATMCellsInfo> cellsList) {
        for (ATMCellsInfo atmCells : cellsList) {
            this.cash.put(atmCells.getNominalInfo(), atmCells.getBanknotesCount());
        }
        return this;
    }

    @Override
    public Issuing build() {
        Issuing issuing = new WhiteIssuing();
        issuing.addCash(cash);
        return issuing;
    }
}
