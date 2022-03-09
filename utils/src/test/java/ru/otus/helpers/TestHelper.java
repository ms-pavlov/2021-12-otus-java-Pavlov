package ru.otus.helpers;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static ru.otus.helpers.PropertiesHelper.prepMsg;
import static ru.otus.helpers.ReflectionHelper.*;

public class TestHelper {

    public static void checkClassExist (String name) {
        try {
            toClass(name);
        } catch (ClassNotFoundException e) {
            fail(prepMsg(PropertiesHelper.TEST, "isAbsent", name));
        }
    }
    public static void checkConstructors (String name, boolean mast) throws ClassNotFoundException {
        if (hasPublicConstructors(toClass(name)) == mast) {
            fail(prepMsg(PropertiesHelper.TEST, "hasExcessConstructors", name));
        }
    }

    public static void checkField (String name, List<String> fields) throws ClassNotFoundException {
        List<Field> elseFields = getElseFields(toClass(name), fields);
        if (!elseFields.isEmpty()) {
            fail(prepMsg(PropertiesHelper.TEST, "hasExcessField", elseFields, name));
        }
    }

    public static String checkMethodsExist (Class<?> clazz, List<String> names, int mod, String msg) {
        return  prepMsg(PropertiesHelper.TEST, msg, noMethods(clazz, names, mod), clazz.getName());
    }

    public static String checkElseMethods (Class<?> clazz, List<String> names, int mod, String msg) {
        return  prepMsg(PropertiesHelper.TEST, msg, getElseMethods(clazz, names, mod), clazz.getName());
    }
}
