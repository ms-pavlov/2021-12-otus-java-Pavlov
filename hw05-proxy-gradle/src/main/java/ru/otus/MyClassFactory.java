package ru.otus;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

import static ru.otus.helpers.ReflectionHelper.getAnnotationMethods;

public class MyClassFactory {
    private MyClassFactory() {
    }

    public static Object createMyClass(Class<?> createClass) {
        try {

            List<Method> logMethods = getAnnotationMethods(createClass, ru.otus.Log.class);
            Object original = createClass.getDeclaredConstructor().newInstance();
            if (logMethods.isEmpty()) {
                return original;
            } else {
                LogInvocationHandler invocationHandler = new LogInvocationHandler(original);

                return Proxy.newProxyInstance(createClass.getClassLoader(),
                        createClass.getInterfaces(),
                        invocationHandler);
            }

        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

}
