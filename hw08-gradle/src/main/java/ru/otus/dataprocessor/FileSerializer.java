package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;
    private final ObjectMapper mapper = new ObjectMapper();

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    /**
     * формирует результирующий json и сохраняет его в файл
     *
     * @param data данные для  записи в файл
     */
    @Override
    public void serialize(Map<String, Double> data) {
        try (var outputFile = new FileOutputStream(fileName)) {
            mapper.writeValue(outputFile, data);
        } catch (IOException exception) {
            exception.getStackTrace();
        }
    }
}
