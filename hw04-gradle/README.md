# otus_java_2021_12
Курс Java Developer. Professional

Группа 2021_12

Павлов Михаил

hw04-gradle - Определение нужного размера хипа

#До оптимизации

Оптимальный размер хипа 768m

-Xms256m-Xmx256m

    java.lang.OutOfMemoryError: Java heap space

-Xms512m -Xmx512m

	spend msec:21464, sec:21
	spend msec:21800, sec:21
	spend msec:22127, sec:22

-Xms640m -Xmx640m

	spend msec:21770, sec:21
	spend msec:21822, sec:21
	spend msec:22107, sec:22

-Xms704m -Xmx704m

    spend msec:20745, sec:20
    spend msec:20934, sec:20
    spend msec:21006, sec:21

-Xms768m -Xmx768m

	spend msec:19986, sec:19
	spend msec:20505, sec:20
	spend msec:20100, sec:20

-Xms896m -Xmx896m

	spend msec:20761, sec:20
	spend msec:20306, sec:20
	spend msec:20557, sec:20

-Xms1G -Xmx1G

    spend msec:20712, sec:20
    spend msec:20120, sec:20
    spend msec:19648, sec:19

-Xms2G -Xmx2G

	spend msec:20784, sec:20
	spend msec:19705, sec:19
	spend msec:20556, sec:20

#После оптимизации

Оптимальный размер хипа 2G

-Xms256m -Xmx256m

    spend msec:12789, sec:12
    spend msec:12684, sec:12
    spend msec:12706, sec:12

-Xms1G -Xmx1G

	spend msec:10500, sec:10
	spend msec:10522, sec:10
	spend msec:10485, sec:10

-Xms1536m -Xmx1536m

	spend msec:9021, sec:9
	spend msec:9017, sec:9
	spend msec:8995, sec:8

-Xms1920m -Xmx1920m

	spend msec:7955, sec:7
	spend msec:8038, sec:8
	spend msec:7904, sec:7

-Xms2G -Xmx2G

	spend msec:8046, sec:8
	spend msec:7755, sec:7
	spend msec:7738, sec:7

-Xms2176m -Xmx2176m

	spend msec:8128, sec:8
	spend msec:7932, sec:7
	spend msec:8045, sec:8

-Xms2560m -Xmx2560m

	spend msec:8013, sec:8
	spend msec:7976, sec:7
	spend msec:8007, sec:8

Домашнее задание.

    Цель:
    на примере простого приложения понять какое влияние оказывают сборщики мусора

    Описание/Пошаговая инструкция выполнения домашнего задания:
    Есть готовое приложение (модуль homework)
    Запустите его с размером хипа 256 Мб и посмотрите в логе время выполнения.
    Пример вывода:
    spend msec:18284, sec:18
    Увеличьте размер хипа до 2Гб, замерьте время выполнения.
    Результаты запусков записывайте в таблицу.
    Определите оптимальный размер хипа, т.е. размер, превышение которого,
    не приводит к сокращению времени выполнения приложения.
    Оптимизируйте работу приложения.
    Т.е. не меняя логики работы (но изменяя код), сделайте так, чтобы приложение работало быстро с минимальным хипом.
    Повторите измерения времени выполнения программы для тех же значений размера хипа.
