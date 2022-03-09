package ru.otus.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReflectionHelper {

    private ReflectionHelper() {
    }

    public static Object getFieldValue(Object object, String name) {
        try {
            var field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void setFieldValue(Object object, String name, Object value) {
        try {
            var field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object callMethod(Object object, String name, Object... args) {
        try {
            var method = object.getClass().getDeclaredMethod(name, toClasses(args));
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            System.out.println(List.of(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }


    public static void callMethod(Object object, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(object, args);
    }

    public static void callMethods(Object object, List<Method> methods, Object... args) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            callMethod(object, method);
        }
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }

    //--------------------------------------------------------------
    public static Class<?> toClass(String name) throws ClassNotFoundException {
        return Class.forName(name);
    }
//--------------------------------------------------------------

    public static Constructor<?>[] getClassConstructors(Class<?> clazz) {
        return clazz.getConstructors();
    }

    public static List<Constructor<?>> getPublicConstructors(Class<?> clazz) {
        return Arrays.stream(getClassConstructors(clazz))
                .filter(ReflectionHelper::isConstructorPublic)
                .toList();
    }

    public static boolean isConstructorPublic(Constructor<?> constructor) {
        return Modifier.isPublic(constructor.getModifiers());
    }

    public static boolean hasPublicConstructors(Class<?> clazz) {
        return getPublicConstructors(clazz).isEmpty();
    }
//--------------------------------------------------------------

    public static List<Method> getMethods(Class<?> clazz, List<String> names) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter((Method method) -> names.contains(method.getName()))
                .toList();
    }

    public static List<Method> getMethods(Class<?> clazz, List<String> names, int mod) {
        return getMethods(clazz, names).stream()
                .filter((Method method) -> (mod & method.getModifiers()) != 0)
                .toList();
    }

    public static List<String> noMethods(Class<?> clazz, List<String> names, int mod) {
        return names.stream().filter((String name) -> !hasMethod(clazz, name, mod)).toList();
    }

    public static List<Method> getAnnotationMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter((Method method) -> method.isAnnotationPresent(annotation))
                .toList();
    }

    public static List<Method> getElseMethods(Class<?> clazz, List<String> names, int mod) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter((Method method) -> (!names.contains(method.getName()) && ((mod & method.getModifiers()) != 0)))
                .toList();
    }

    public static boolean hasMethod(Class<?> clazz, String name, int mod) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .anyMatch((Method method) -> ((name.equals(method.getName())) && ((mod & method.getModifiers()) != 0)));
    }

    public static List<Field> getElseFields(Class<?> clazz, List<String> names) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter((Field field) -> (!names.contains(field.getName())))
                .toList();
    }

}
