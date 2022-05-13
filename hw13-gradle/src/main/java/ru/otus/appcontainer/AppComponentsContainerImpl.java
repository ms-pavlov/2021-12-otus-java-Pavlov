package ru.otus.appcontainer;

import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

import static ru.otus.helpers.ReflectionHelper.getAnnotationMethods;
import static ru.otus.helpers.ReflectionHelper.instantiate;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) {
        this(Arrays.stream(initialConfigClasses));
    }

    public AppComponentsContainerImpl(String packageName) {
        this(new Reflections(packageName).getTypesAnnotatedWith(AppComponentsContainerConfig.class).stream());
    }

    private AppComponentsContainerImpl(Stream<Class<?>> classesStream) {
        classesStream
                .filter(clazz -> clazz.isAnnotationPresent(AppComponentsContainerConfig.class))
                .sorted(Comparator.comparingInt(clazz -> clazz.getAnnotation(AppComponent.class).order()))
                .forEach(this::processConfig);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        getAnnotationMethods(configClass, AppComponent.class).stream()
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                    var componentName = method.getAnnotation(AppComponent.class).name();
                    appComponentsByName.put(componentName, invokeComponent(method, prepareComponent(configClass)));
                    appComponents.add(getAppComponent(componentName));
                });
    }

    private Object prepareComponent(Class<?> configClass) {
        var constructors = configClass.getDeclaredConstructors();
        if (1 == constructors.length) {
            return instantiate(configClass, prepareArguments(constructors[0].getParameterTypes()));
        }
        return instantiate(configClass);
    }

    private Object[] prepareArguments(Class<?>[] types) {
        return Arrays.stream(types)
                .map(this::getAppComponent)
                .toArray();
    }


    private Object invokeComponent(Method method, Object component) {
        try {
            method.setAccessible(true);
            return method.invoke(component, prepareArguments(method.getParameterTypes()));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(o -> o.getClass().isAssignableFrom(componentClass) || Arrays.stream(o.getClass().getInterfaces()).toList().contains(componentClass))
                .findAny().orElse(null);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
