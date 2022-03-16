package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static ru.otus.helpers.PropertiesHelper.*;

public class SimpleMeasurementJsonParser implements Parser<List<Measurement>> {
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String NAME_FIELD = "name";
    private static final String VALUE_FIELD = "value";

    @Override
    public List<Measurement> parse(InputStream inputStream) throws FileProcessException {
        return read(inputStream)
                .stream()
                .map(SimpleMeasurementJsonParser::parseFieldsToMeasurement)
                .toList();
    }

    /**
     * Читает даные из в ходного потока в набот пар ключ-значение List<Map<String, String>>.
     *
     * @param inputStream входной поток.
     * @return набот пар ключ-значение List<Map<String, String>>.
     * @throws FileProcessException исключение если поток не доступен для чтения или содержит не валидные данные
     */
    private List<Map<String, String>> read(InputStream inputStream) throws FileProcessException {
        try {
            return this.mapper.readValue(inputStream, new TypeReference<>() {});
        } catch (JsonParseException | JsonMappingException exception) {
            throw new FileProcessException(errorMessage("jsonParserError",
                    exception.getClass().getName(),
                    exception.getMessage()));
        } catch (IOException | IllegalArgumentException exception) {
            throw new FileProcessException(errorMessage("ioError",
                    exception.getClass().getName(),
                    exception.getMessage()));
        }
    }

    /**
     * Парсит набор ключ-значение в Measurement.
     *
     * @param fields набор ключ-значение Map<String, String>.
     * @return возвращает Measurement.
     */
    private static Measurement parseFieldsToMeasurement(Map<String, String> fields) throws FileProcessException {
        try {
            return new Measurement(fields.get(NAME_FIELD), Double.parseDouble(fields.get(VALUE_FIELD)));
        } catch (NumberFormatException exception) {
            throw new FileProcessException(errorMessage("wrongNumberFormat")
                    .replaceAll("%value%", fields.get(VALUE_FIELD)));
        }
    }
}
