package ru.otus.helpers;

import java.util.List;
import java.util.ResourceBundle;

public class PropertiesHelper {
    public static final String BR = "\n\r";
    public static final String CONFIG = "application";
    public static final String ERROR = "errorMessage";
    public static final String TEST = "testMessage";


    private PropertiesHelper() {

    }

    public static String getProperty(String properties, String propertyName) {
        return ResourceBundle.getBundle(properties).getString(propertyName);
    }

    public static String getErrorConfig() {
        return getProperty(CONFIG, ERROR);
    }

    public static String getTestConfig() {
        return getProperty(CONFIG, TEST);
    }

    public static String errorMessage(String errorMessageName) {
        return ResourceBundle.getBundle(getErrorConfig()).getString(errorMessageName);
    }

    public static String errorMessage(String errorMessageName, String name, String value) {
        return ResourceBundle
                .getBundle(getErrorConfig())
                .getString(errorMessageName)
                .replaceAll("%name%", name)
                .replaceAll("%value%", value);
    }

    public static String testMessage(String testMessageName) {
        return ResourceBundle.getBundle(getTestConfig()).getString(testMessageName);
    }

    public static String prepMsg(String properties, String msg, String name) {
        return getProperty(properties, msg)
                .replaceAll("%name%", name) + BR;
    }

    public static String prepMsg(String properties, String msg, List<?> list, String name) {
        if (list.size() > 0) {
            return prepMsg(properties, msg, list.toString(), name);
        }
        return "";
    }

    public static String prepMsg(String properties, String msg, String value, String name) {
        return getProperty(properties, msg)
                .replaceAll("%name%", name)
                .replaceAll("%value%", value) + BR;
    }

}
