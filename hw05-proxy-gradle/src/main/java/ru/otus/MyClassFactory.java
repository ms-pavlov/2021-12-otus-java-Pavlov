package ru.otus;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public class MyClassFactory {
    private MyClassFactory() {
    }

    public static Object createMyClass(Class<?> createClass) {
        try {
            List<Method> logMethods = getLogedMethods(createClass);
            Object original = createClass.getDeclaredConstructor().newInstance();
            if (logMethods.isEmpty()) {
                return original;
            } else {
                LogInvocationHandler invocationHandler = new LogInvocationHandler(original);
                Object proxy = Proxy.newProxyInstance(createClass.getClassLoader(),
                        createClass.getInterfaces(),
                        invocationHandler);

                return proxy;
            }

        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Method> getLogedMethods(Class<?> createClass) {
        return Arrays.stream(createClass.getMethods())
                .filter((Method method) -> method.isAnnotationPresent(ru.otus.Log.class))
                .toList();
    }

}
