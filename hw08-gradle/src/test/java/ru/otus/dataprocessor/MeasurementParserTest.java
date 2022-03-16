package ru.otus.dataprocessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MeasurementParserTest {

    @Test
    @DisplayName("Проверка парсера при неверном значении value.")
    void parseCheck1() {
        String badJson = "[{\"name\":\"val1\",\"value\": \"badJson\"}]";
        var inputStream = new ByteArrayInputStream(badJson.getBytes(StandardCharsets.UTF_8));
        assertThrows(FileProcessException.class, () -> ParserFactory.getMeasurementParser().parse(inputStream));
    }

    @Test
    @DisplayName("Проверка парсера для не json")
    void parseCheck2() {
        String veryBadJson = "It is very bad json";
        var inputStream = new ByteArrayInputStream(veryBadJson.getBytes(StandardCharsets.UTF_8));
        assertThrows(FileProcessException.class, () -> ParserFactory.getMeasurementParser().parse(inputStream));
    }

    @Test
    @DisplayName("Проверка парсера при inputStream = null")
    void parseCheck3() {
        assertThrows(FileProcessException.class, () -> ParserFactory.getMeasurementParser().parse(null));
    }


}