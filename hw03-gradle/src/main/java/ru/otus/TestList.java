package ru.otus;


import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import static org.assertj.core.api.AssertionsForClassTypes.*;

public class TestList {

    public TestList() {
    }

    @Before
    void doBefore() {
        System.out.println("doBefore");
    }

    @ru.otus.annotations.Test
    void doTest1() {
        System.out.println("doTest1 Успешный Тест");
    }

    @ru.otus.annotations.Test
    void doTest2() {
        System.out.println("doTest2 Успешный Тест");
    }

    @ru.otus.annotations.Test
    void doFailedTest() {
        System.out.println("doFailedTest Провальный Тест");
        fail("Упало!!");
    }

    @After
    void doAfter() {
        System.out.println("doAfter");
    }

}