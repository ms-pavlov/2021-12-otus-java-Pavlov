package ru.otus.helpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static ru.otus.helpers.ReflectionHelper.toClass;
import static ru.otus.helpers.TestHelper.*;

public class ReflectionHelperTest {
    public static final String CALSS_NAME = "ru.otus.helpers.ReflectionHelper";
    public static final String[] STATIC_METHODS = {"getFieldValue", "setFieldValue", "callMethod",
            "instantiate", "toClasses", "toClass", "getClassConstructors", "getPublicConstructors",
            "hasPublicConstructors", "getMethods", "noMethods", "getElseMethods", "hasMethod",
            "getElseFields", "isConstructorPublic", "getAnnotationMethods", "callMethods"};
    public static final String[] FIELDS = {};

    @Test
    @DisplayName("Проверяем, что класс " + CALSS_NAME + " существует")
    void isExist() {
        checkClassExist(CALSS_NAME);
    }

    @Test
    @DisplayName("Проверяем, что класс " + CALSS_NAME + " имеет публичный конструктор")
    void hasConstructors() throws ClassNotFoundException {
        checkConstructors(CALSS_NAME, false);
    }

    @Test
    @DisplayName("Проверяем, что у класса " + CALSS_NAME + " все в порядке c полями")
    void fieldsCheck() throws ClassNotFoundException {
        checkField(CALSS_NAME, List.of(FIELDS));
    }

    @Test
    @DisplayName("Проверяем, что у класса " + CALSS_NAME + " все в порядке с методами")
    void hasMethods() throws ClassNotFoundException {
        Class<?> clazz = toClass(CALSS_NAME);

        String msg = "";
        msg += checkMethodsExist(clazz, List.of(STATIC_METHODS), Modifier.STATIC|Modifier.PUBLIC, "noPublicMethods");
        msg += checkElseMethods(clazz, List.of(STATIC_METHODS), Modifier.PUBLIC, "hasExcessMethods");

        if (msg.length() > 0) {
            fail(msg);
        }
    }

}
