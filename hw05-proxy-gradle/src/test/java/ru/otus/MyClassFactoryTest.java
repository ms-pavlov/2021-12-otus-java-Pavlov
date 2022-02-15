package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static ru.otus.helpers.ReflectionHelper.toClass;
import static ru.otus.helpers.TestHelper.*;

public class MyClassFactoryTest {
    public static final String CLASS_NAME = "ru.otus.MyClassFactory";
    public static final String[] STATIC_METHODS = {"createMyClass", "getLogedMethods" };

    @Test
    @DisplayName("Проверяем, что класс " + CLASS_NAME + " существует")
    void isExist() {
        checkClassExist(CLASS_NAME);
    }

    @Test
    @DisplayName("Проверяем, что класс " + CLASS_NAME + " имеет публичный конструктор")
    void hasConstructors() throws ClassNotFoundException {
        checkConstructors(CLASS_NAME, false);
    }

    @Test
    @DisplayName("Проверяем, что у класса " + CLASS_NAME + " все в порядке с методами")
    void hasMethods() throws ClassNotFoundException {
        Class<?> clazz = toClass(CLASS_NAME);
        String msg = "";
        msg += checkMethodsExist(clazz, List.of(STATIC_METHODS), Modifier.STATIC|Modifier.PUBLIC, "noPublicMethods");
        msg += checkElseMethods(clazz, List.of(STATIC_METHODS), Modifier.PUBLIC, "hasExcessMethods");

        if (msg.length() > 0) {
            fail(msg);
        }
    }

}
