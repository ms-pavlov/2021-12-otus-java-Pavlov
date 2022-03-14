package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.io.InputStream;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    public ResourcesFileLoader(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("test.csv");
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        return null;
    }
}
