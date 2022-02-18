package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.helpers.ReflectionHelper.toClass;
import static ru.otus.helpers.TestHelper.*;
import static ru.otus.helpers.TestHelper.checkElseMethods;

class LogInvocationHandlerTest {
    public static final String CLASS_NAME = "ru.otus.LogInvocationHandler";
    public static final String[] PUBLIC_METHODS = {"invoke"};
    public static final String[] PRIVAPE_METHODS = {"doLog" };

    @Test
    @DisplayName("Проверяем, что класс " + CLASS_NAME + " существует")
    void isExist() {
        checkClassExist(CLASS_NAME);
    }

    @Test
    @DisplayName("Проверяем, что у класса " + CLASS_NAME + " все в порядке с методами")
    void hasMethods() throws ClassNotFoundException {
        Class<?> clazz = toClass(CLASS_NAME);
        String msg = "";
        msg += checkMethodsExist(clazz, List.of(PUBLIC_METHODS), Modifier.PUBLIC, "noPublicMethods");
        msg += checkMethodsExist(clazz, List.of(PRIVAPE_METHODS), Modifier.PRIVATE, "noPrivateMethods");
        msg += checkElseMethods(clazz, List.of(PUBLIC_METHODS), Modifier.PUBLIC, "hasExcessMethods");
        msg += checkElseMethods(clazz, List.of(PRIVAPE_METHODS), Modifier.PRIVATE, "hasExcessMethods");

        if (msg.length() > 0) {
            fail(msg);
        }
    }

    @Test
    void invoke() {
    }
}