package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class MeasurementParser implements Parser<List<Measurement>> {
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String NAME_FIELD = "name";
    private static final String VALUE_FIELD = "value";

    @Override
    public List<Measurement> parse(InputStream inputStream) throws IOException {
        return read(inputStream)
                .stream()
                .map(MeasurementParser::parseFieldsToMeasurement)
                .toList();
    }

    /**
     * Читает даные из в ходного потока в набот пар ключ-значение List<Map<String, String>>.
     *
     * @param inputStream входной поток.
     * @return набот пар ключ-значение List<Map<String, String>>.
     * @throws IOException исключение если файл не доступен для чтения
     */
    private List<Map<String, String>> read(InputStream inputStream) throws IOException {
        return this.mapper.readValue(inputStream, new TypeReference<>() {});
    }

    /**
     * Парсит набор ключ-значение в Measurement.
     *
     * @param fields набор ключ-значение Map<String, String>.
     * @return возвращает Measurement.
     */
    private static Measurement parseFieldsToMeasurement(Map<String, String> fields) {
        return new Measurement(fields.get(NAME_FIELD), Double.parseDouble(fields.get(VALUE_FIELD)));
    }
}
