package ru.otus.dataprocessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementParserTest {

    @Test
    @DisplayName("Проверка парсера.")
    void parse() {
        String badJson = "[{\"name\":\"val1\",\"value\": \"badJson\"}]";
        var inputStream = new ByteArrayInputStream(badJson.getBytes(StandardCharsets.UTF_8));
        var message = assertThrows(FileProcessException.class, () -> ParserFactory.getMeasurementParser().parse(inputStream)).getMessage();
        var expected = ResourceBundle
                .getBundle("messages")
                .getString("wrongNumberFormat")
                .replaceAll("%value%", "badJson");

        assertEquals(expected, message);

    }
}