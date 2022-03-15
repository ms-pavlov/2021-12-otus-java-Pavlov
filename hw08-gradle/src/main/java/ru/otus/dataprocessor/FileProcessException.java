package ru.otus.dataprocessor;

import java.util.ResourceBundle;

public class FileProcessException extends RuntimeException {
    public FileProcessException(Exception ex) {
        super(ex);
    }

    public FileProcessException(String msg) {
        super(msg);
    }
    public static FileProcessException makeException (String messageKey) {
        return new FileProcessException(ResourceBundle
                .getBundle("messages")
                .getString(messageKey));
    }
    public static FileProcessException makeException (String messageKey, String value) {
        return new FileProcessException(ResourceBundle
                .getBundle("messages")
                .getString(messageKey)
                .replaceAll("%value%", value));
    }
}
