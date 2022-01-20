/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.otus;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


/**
 * To start the application:
 * ./gradlew build
 * java -jar ./hw01-gradle/build/libs/hw01-gradle-0.1.jar
 * <p>
 * To unzip the jar:
 * unzip -l hw01-gradle.jar
 * unzip -l hw01-gradle-0.1.jar
 */
public class HelloOtus {
    final static String LANGUAGE = "en"; // Язык
    final static int MAX_NUMBER = 10; // Количество строк в песне
    final static ImmutableMap<String, String> WORDS_99_EN = ImmutableMap.<String, String>builder()
            .put("last", "Go to the store and buy some more")
            .put("second last", "No more bottles of beer on the wall, no more bottles of beer.")
            .put("one bottle", "%i bottle of beer on the wall, %i bottle of beer.")
            .put("bottles", "%i bottles of beer on the wall, %i bottles of beer.")
            .put("no bottles", "Take one down and pass it around, No more bottles of beer on the wall")
            .put("second bottles", "Take one down and pass it around,  %i bottle of beer on the wall.")
            .build(); // Строки пести и использование guava

    static String upperFirstAtLine(String line) {
        if ((Optional.fromNullable(line).isPresent()) && (line.length() > 0)) {
            return line.substring(0, 1).toUpperCase() + line.substring(1);
        } else {
            return line;
        }
    }

    static String getLine(String key, String number) {
        if ((WORDS_99_EN.containsKey(key))&&(Optional.fromNullable(WORDS_99_EN.get(key)).isPresent())) {
            return upperFirstAtLine(Objects.requireNonNull(WORDS_99_EN.get(key)).replaceAll("%i", Optional.fromNullable(number).or(""))); // по умолчанию
        }
        return "";
    }

    // Собирать песню задом наперед удобнее
    static void addTwoLineBack(String first, String second, int number, List<String> example) {
        RuleBasedNumberFormat numberformat = new RuleBasedNumberFormat(
                Locale.forLanguageTag(LANGUAGE),
                RuleBasedNumberFormat.SPELLOUT);
        example.add(getLine(first, numberformat.format(number - 1)));
        example.add(getLine(second, numberformat.format(number)));
    }

    static void songBottles() {
        List<String> example = new ArrayList<>();
        // Собирать песню задом наперед удобнее
        addTwoLineBack("last", "second last", 0, example);
        int i = 1;
        addTwoLineBack("no bottles", "one bottle", i++, example);
        while (i < MAX_NUMBER) {
            addTwoLineBack("second bottles", "bottles", i++, example);
        }
        Lists.reverse(example).forEach(System.out::println);
    }

    static void sayHello() {
        System.out.println("Hello Otus!!");
        System.out.println();
    }

    public static void main(String... args) {
        sayHello();
        songBottles();

    }
}
