package ru.otus;

/**
 * To start the application:
 * ./gradlew build
 * java -jar ./hw05-proxy-gradle/build/libs/hw05-proxy-gradle-0.1.jar
 */

public class Main {
    public static void main(String[] args) {
        TestLogging test = (TestLogging) MyClassFactory.createMyClass(TestLoggingImpl.class);
        System.out.println(test.calculation(1));
        System.out.println(test.calculation(1, 2));
        System.out.println(test.calculation(1, 2, 3));
        int[] params = {1, 2, 3, 4, 5};
        System.out.println(test.calculation(params));
    }
}
