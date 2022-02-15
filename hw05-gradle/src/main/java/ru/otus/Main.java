package ru.otus;
/*
    java -jar ./hw05-gradle/build/libs/hw05-gradle-0.1.jar
    java -javaagent:./hw05-asm-gradle/build/libs/hw05-agent-0.1.jar -jar ./hw05-gradle/build/libs/hw05-gradle-0.1.jar
*/
public class Main {
    public static void main(String[] args) {
        System.out.println(TestLogging.calculation(1, 2));
        int[] test = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(TestLogging.calculation(test));
        System.out.println(TestLogging.calculation(1, 2, 3));
    }
}
