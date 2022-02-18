package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LogInvocationHandler implements InvocationHandler {
    Object original;
    Map<Method, Boolean> logable;

    public LogInvocationHandler(Object original) {
        this.original = original;
        logable = new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Первый вызов метода дорогой
        if (!logable.containsKey(method)) {
            Method originalMethod = original.getClass().getMethod(method.getName(), method.getParameterTypes());
            logable.put(method, originalMethod.isAnnotationPresent(Log.class));
        }
        if (logable.get(method)) {
            doLog(method, args);
        }
        return method.invoke(original, args);
    }

    private void doLog(Method method, Object[] args) {
        System.out.print(method.getName() + ": ");
        System.out.println(Arrays.toString(args));
    }
}
