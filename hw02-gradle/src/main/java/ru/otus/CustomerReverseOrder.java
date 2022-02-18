package ru.otus;


import java.util.Deque;
import java.util.LinkedList;

public class CustomerReverseOrder {

    private final Deque<Customer> deque;

    public CustomerReverseOrder() {
        this.deque = new LinkedList<>(); //возможно ArrayDeque лучше. Подойдет любая имплементация Deque
    }

    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        deque.push(customer); // метод Deque
    }

    public Customer take() {
        return deque.pop(); // метод Deque
    }
}
