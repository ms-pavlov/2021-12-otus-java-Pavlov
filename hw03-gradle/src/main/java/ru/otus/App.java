package ru.otus;

/**
 * To start the application:
 * ./gradlew build
 * java -jar ./hw03-gradle/build/libs/hw03-gradle-0.1.jar
 * <p>
 * To unzip the jar:
 * unzip -l hw03-gradle.jar
 * unzip -l hw03-gradle-0.1.jar
 */

public class App {
    public static void main(String... args)  {

        System.out.println(Tester.run("ru.otus.TestList"));
    }
}
