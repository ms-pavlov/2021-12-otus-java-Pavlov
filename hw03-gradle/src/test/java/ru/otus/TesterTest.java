package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.helpers.PropertiesHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static ru.otus.helpers.PropertiesHelper.getProperty;
import static ru.otus.helpers.PropertiesHelper.prepMsg;
import static ru.otus.helpers.ReflectionHelper.toClass;
import static ru.otus.helpers.TestHelper.*;

public class TesterTest {
    public static final String CALSS_NAME = "ru.otus.Tester";
    public static final String INIT_METHOD = "run";
    public static final String[] PUBLIC_METHODS = {"run"};
    public static final String[] PRIVATE_METHODS = {"doTestes","doTest", "makeStatistics"};
    public static final String[] FIELDS = {};

    @Test
    @DisplayName("Проверяем, что класс " + CALSS_NAME + " существует")
    void isExist() {
        checkClassExist(CALSS_NAME);
    }

    @Test
    @DisplayName("Проверяем, что класс " + CALSS_NAME + " имеет публичный конструктор")
    void hasConstructors() throws ClassNotFoundException {
        checkConstructors(CALSS_NAME, false);
    }

    @Test
    @DisplayName("Проверяем, что у класса " + CALSS_NAME + " все в порядке с методами")
    void hasMethods() throws ClassNotFoundException {
        Class<?> clazz = toClass(CALSS_NAME);

        String msg = "";
        msg += checkMethodsExist(clazz, List.of(PUBLIC_METHODS), Modifier.PUBLIC, "noPublicMethods");
        msg += checkMethodsExist(clazz, List.of(PRIVATE_METHODS), Modifier.PRIVATE, "noPrivateMethods");
        msg += checkElseMethods(clazz, List.of(PUBLIC_METHODS), Modifier.PUBLIC, "hasExcessMethods");
        msg += checkElseMethods(clazz, List.of(PRIVATE_METHODS), Modifier.PRIVATE, "hasExcessMethods");

        if (msg.length() > 0) {
            fail(msg);
        }
    }

    @Test
    @DisplayName("Проверяем, что у класса " + CALSS_NAME + " все в порядке c полями")
    void fieldsCheck() throws ClassNotFoundException {
        checkField(CALSS_NAME, List.of(FIELDS));
    }

    @Test
    @DisplayName("Проверяем, метод run")
    void hasRun() throws ClassNotFoundException {
        Class<?> clazz = toClass(CALSS_NAME);
        Method run = null;
        try {
            run = clazz.getDeclaredMethod(INIT_METHOD, String.class);
        } catch (NoSuchMethodException e) {
            fail(prepMsg(PropertiesHelper.TEST, "noRunMethod", INIT_METHOD, CALSS_NAME));
        }

        if (!"java.lang.String".equals(run.getReturnType().getName())) {
            fail(prepMsg(PropertiesHelper.TEST, "runReturnType", run.getReturnType().getName(), CALSS_NAME));
        }

        assertThat(Tester.run("ru.otus.TestList1"))
                .isEqualTo(prepMsg("test", "isAbsent", "ru.otus.TestList1"));
    }

    @Test
    @DisplayName("Проверяем, метод makeStatistics")
    void statistics() {
        assertThat(Tester.run("ru.otus.App"))
                .isEqualTo(getProperty("test", "testStatistics")
                        .replaceAll("%pass%", String.valueOf(0))
                        .replaceAll("%fail%", String.valueOf(0))
                        .replaceAll("%all%", String.valueOf(0)));
    }


}
