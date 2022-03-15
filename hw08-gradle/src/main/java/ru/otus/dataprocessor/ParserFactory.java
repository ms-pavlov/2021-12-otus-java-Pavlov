package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;

public class ParserFactory {
    public static Parser<List<Measurement>> getMeasurementParser() {
        return new MeasurementParser();
    }
}
