package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ResourcesFileLoader implements Loader {
    private final String fileName;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String NAME_FIELD = "name";
    private static final String VALUE_FIELD = "value";


    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * читает файл, парсит и возвращает результат
     *
     * @return возвращает результат в виде List<Measurement>, если файл не доступен возвращает null
     */
    @Override
    public List<Measurement> load() {
        try (var resourceInputFile = ResourcesFileLoader.class
                .getClassLoader()
                .getResourceAsStream(this.fileName)) {
            return parse(readFile(resourceInputFile));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Читает файл в набот пар ключ-значение List<Map<String, String>>.
     *
     * @param inputStream входной поток.
     * @return набот пар ключ-значение List<Map<String, String>>.
     * @throws IOException исключение если файл не доступен для чтения
     */
    private List<Map<String, String>> readFile(InputStream inputStream) throws IOException {
        return this.mapper.readValue(inputStream, new TypeReference<>() {
        });
    }

    private List<Measurement> parse(List<Map<String, String>> fieldsMap) {
        return fieldsMap
                .stream()
                .map(ResourcesFileLoader::parseFieldsToMeasurement)
                .toList();
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
