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
    156 стабатываний GC

-Xms640m -Xmx640m

	spend msec:21770, sec:21
	spend msec:21822, sec:21
	spend msec:22107, sec:22
    115 стабатывание GC

-Xms704m -Xmx704m

    spend msec:20745, sec:20
    spend msec:20934, sec:20
    spend msec:21006, sec:21
    92 стабатывания GC

-Xms768m -Xmx768m

	spend msec:19986, sec:19
	spend msec:20505, sec:20
	spend msec:20100, sec:20
    78 срабатываний GC

-Xms896m -Xmx896m

	spend msec:20761, sec:20
	spend msec:20306, sec:20
	spend msec:20557, sec:20
    72 стабатывания GC

-Xms1G -Xmx1G

    spend msec:20712, sec:20
    spend msec:20120, sec:20
    spend msec:19648, sec:19
    60 срабатываний GC

-Xms2G -Xmx2G

	spend msec:20784, sec:20
	spend msec:19705, sec:19
	spend msec:20556, sec:20
    49 срабатываний GC

#После оптимизации

Оптимальный размер хипа 2m

-Xms2m -Xmx2m

	spend msec:1777, sec:1
	spend msec:1775, sec:1
	spend msec:1776, sec:1

-Xms16m -Xmx16m

	spend msec:1773, sec:1
	spend msec:1777, sec:1
	spend msec:1776, sec:1

-Xms64m -Xmx64m

	spend msec:1789, sec:1
	spend msec:1788, sec:1
	spend msec:1779, sec:1

-Xms256m -Xmx256m

    spend msec:1772, sec:1
    spend msec:1765, sec:1
    spend msec:1773, sec:1

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
