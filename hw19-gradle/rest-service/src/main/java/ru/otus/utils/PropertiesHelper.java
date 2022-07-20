package ru.otus.utils;

import java.util.ResourceBundle;

public class PropertiesHelper {
    private static final String MESSAGES = "messages";

    public static String getFormatMessages(String messageName, Object... args) {
        return String.format(ResourceBundle.getBundle(MESSAGES).getString(messageName), args);
    }
}
