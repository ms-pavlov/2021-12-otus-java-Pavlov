package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

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
        try (var resourceInputFileStream = ResourcesFileLoader.class
                .getClassLoader()
                .getResourceAsStream(this.fileName)) {
            return ParserFactory.getMeasurementParser().parse(resourceInputFileStream);
        } catch (IOException | FileProcessException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
