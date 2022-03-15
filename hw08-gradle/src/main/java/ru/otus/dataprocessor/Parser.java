package ru.otus.dataprocessor;

import java.io.IOException;
import java.io.InputStream;

public interface Parser<M>{
    M parse(InputStream inputStream) throws FileProcessException;
}
