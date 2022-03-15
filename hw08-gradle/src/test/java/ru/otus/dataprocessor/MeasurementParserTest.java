package ru.otus.dataprocessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementParserTest {

    @Test
    @DisplayName("Проверка парсера при неверном значении value.")
    void parseCheck1() {
        String badJson = "[{\"name\":\"val1\",\"value\": \"badJson\"}]";
        var inputStream = new ByteArrayInputStream(badJson.getBytes(StandardCharsets.UTF_8));
        var message = assertThrows(FileProcessException.class, () -> ParserFactory.getMeasurementParser().parse(inputStream)).getMessage();
        var expected = ResourceBundle
                .getBundle("messages")
                .getString("wrongNumberFormat")
                .replaceAll("%value%", "badJson");

        assertEquals(expected, message);
    }
    @Test
    @DisplayName("Проверка парсера для не json")
    void parseCheck2() {
        String veryBadJson = "It is very bad json";
        var inputStream = new ByteArrayInputStream(veryBadJson.getBytes(StandardCharsets.UTF_8));

        var message = assertThrows(FileProcessException.class, () -> ParserFactory.getMeasurementParser().parse(inputStream)).getMessage();
        var expected = ResourceBundle
                .getBundle("messages")
                .getString("jsonParserError");
        assertEquals(expected, message);
    }

    @Test
    @DisplayName("Проверка парсера при inputStream = null")
    void parseCheck3() {
        var message = assertThrows(FileProcessException.class, () -> ParserFactory.getMeasurementParser().parse(null)).getMessage();
        var expected = ResourceBundle
                .getBundle("messages")
                .getString("ioError");
        assertEquals(expected, message);
    }


}