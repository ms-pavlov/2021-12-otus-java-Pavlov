package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.helpers.PropertiesHelper.*;
import static ru.otus.helpers.ReflectionHelper.*;

public class Tester {

    private Tester() {

    }

    public static String run(String className) {
        return (new Tester()).doTestes(className);
    }

    private String doTestes(String className) {
        Map<String, String> statistics = new HashMap<>();
        try {
            var clazz = toClass(className);
            var before = getAnnotationMethods(clazz, Before.class); // Все методы с @Before
            var tests = getAnnotationMethods(clazz, Test.class); // Все методы с @Test
            var after = getAnnotationMethods(clazz, After.class); // Все методы с @After
            for (Method test : tests) {
                statistics.put(test.getName(), doTest(clazz, before, after, test));
            }
        } catch (ClassNotFoundException e) {
            return prepMsg("test", "isAbsent", className);
        }
        return makeStatistics(statistics);
    }

    private String doTest(Class<?> clazz, List<Method> before, List<Method> after, Method test) {
        String msg = "";
        try {
            Object testObject = clazz.getConstructor().newInstance();
            callMethods(testObject, before); // все before
            try {
                callMethod(testObject, test); // сделать тест
            } catch (InvocationTargetException e) {
                msg = e.getTargetException().getMessage() + BR; // поймать сообщение
            }
            callMethods(testObject, after); // все after
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return e.toString(); // Исключения в конструторе, before  и after.
        } catch (NoSuchMethodException e) {
            return prepMsg("test", "noConstructors", clazz.getName());
        }
        return msg;
    }

    private String makeStatistics (Map<String, String> statistics) {
        String msg = "";
        int pass = 0;
        int fail = 0;

        for (Map.Entry<String, String> entry : statistics.entrySet()) {
            if (entry.getValue().isEmpty()) {
                pass++;
                msg = msg.concat(prepMsg("test", "testStart", "PASSED", entry.getKey()));
            } else {
                fail++;
                msg = msg.concat(prepMsg("test", "testStart", "FAILED", entry.getKey()));
                msg = msg.concat(entry.getValue());
            }
        }
        msg = msg.concat(getProperty("test", "testStatistics")
                .replaceAll("%pass%", String.valueOf(pass))
                .replaceAll("%fail%", String.valueOf(fail))
                .replaceAll("%all%", String.valueOf(pass+fail)));

        return msg;
    }

}
