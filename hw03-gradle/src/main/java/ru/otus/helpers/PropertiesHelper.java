package ru.otus.helpers;

import java.util.List;
import java.util.ResourceBundle;

public class PropertiesHelper {

    public static final String TEST = "test";
    public static final String REFLECTION = "reflection";
    public static final String BR = "\n\r";

    private PropertiesHelper() {

    }

    public static String getProperty(String properties, String propertyName) {
        return ResourceBundle.getBundle(properties).getString(propertyName);
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
