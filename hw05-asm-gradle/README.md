# otus_java_2021_12
Курс Java Developer. Professional

Группа 2021_12

Павлов Михаил

hw05-gradle, hw05-asm-gradle - 
Байт код, class-loader, инструментация, asm. ДЗ 

    Домашнее задание
    Автоматическое логирование с ASM (агент).

    Цель:
        Понять как реализуется AOP, какие для этого есть технические средства.

    Описание/Пошаговая инструкция выполнения домашнего задания:
        Разработайте такой функционал:
        метод класса можно пометить самодельной аннотацией @Log, например, так:

            class TestLogging {
            @Log
            public void calculation(int param) {};
            }

        При вызове этого метода "автомагически" в консоль должны логироваться значения параметров.
        Например так.

            class Demo {
                public void action() {
                    new TestLogging().calculation(6);
                }
            }

        В консоле дожно быть:
        executed method: calculation, param: 6

        Обратите внимание: явного вызова логирования быть не должно.

        Учтите, что аннотацию можно поставить, например, на такие методы:
            public void calculation(int param1)
            public void calculation(int param1, int param2)
            public void calculation(int param1, int param2, String param3) 